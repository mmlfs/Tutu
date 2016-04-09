#coding=utf-8
import os
import uuid
from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.authtoken.models import Token
from django.utils import timezone

from Tutu import settings
from django.contrib import auth
from django.contrib.auth.models import User
from Tutu.api.img.models import Image,Comment
from Tutu.api.img.serializers import ImageSerializer,CommentSerializer
from bosonnlp import BosonNLP

import urllib2
import urllib
import httplib 
import json
from urllib import quote
from urllib import urlencode



def profile_upload(file):  
    if file:  
        path=os.path.join(settings.MEDIA_ROOT,'upload')  
        #file_name=str(uuid.uuid1())+".jpg"  
        file_name=str(uuid.uuid1())+'-'+file.name  
        #fname = os.path.join(settings.MEDIA_ROOT,filename)  
        path_file=os.path.join(path,file_name)  
        fp = open(path_file, 'wb')  
        for content in file.chunks():   
            fp.write(content)  
        fp.close()  
        return (True,file_name) #change  
    return (False,file_name)   #change  


def getKeyWords(command):
	nlp = BosonNLP("ofW2OZMI.4712.UzT0VvLGGkdi")
	r = nlp.extract_keywords(command, top_k=3)
	l = []
	for k,v in r:
		v = v.encode('utf8')
		l.append(v)
	return l


def getAttitude(comment):
	nlp = BosonNLP('ofW2OZMI.4712.UzT0VvLGGkdi')
	# s = ['他是个傻逼', '美好的世界']
	result = nlp.sentiment(comment)
	print result
	return result

def getFaceInfo(img_url):
	api_id='8c57e478fb874954833171a419d73c2e'
	api_secret='a617e51c757f48d1899c51ac38b2fed4'

	# img_url='http://file2.mafengwo.net/M00/31/0C/wKgBm04YiW7Cz2yfAAEx87h-R4U84.jpeg'
	# img_url='http://365jia.cn/uploads/11/1009/.4e910e9f26cect148x95.jpg'
	# img_url=urllib2.quote(img_url)  
	request_url='https://v1-api.visioncloudapi.com/face/detection'
	request_url='https://v1-api.visioncloudapi.com/face/detection?'+"api_id="+api_id+"&api_secret="+api_secret
	print request_url
	data = {}
	data['url'] = img_url
	data['attributes'] = 1
	post_data = urllib.urlencode(data)

	#get
	# f=urllib.urlopen(request_url)
	# s=f.read()
	# print s

	#post
	f = urllib2.urlopen(request_url, post_data)
	s=f.read()
	return json.loads(s)


class Test(APIView):
	def get(self, request):
		# comment='西门町很多的，就是房间小，环境也没那么好（当然干净还是干净，只是就是高级旅馆的感觉）我住过意舍，说实话一般，不值得特地推荐~'
		# # comment='你妈死了'
		# attitude=getAttitude(comment)
		# keywords=getKeyWords(comment)
		# return Response({"status":0, "info":keywords, "data":attitude})

		s=getFaceInfo('http://file2.mafengwo.net/M00/31/0C/wKgBm04YiW7Cz2yfAAEx87h-R4U84.jpeg')
		return Response({"status":0, "info":"", "data":s})


class UploadFiles(APIView):
	def post(self, request, format=None):
		ret="0"  
		file = request.FILES.get("Filedata",None)  
		if file:  
			result,new_name=profile_upload(file)  
			if result:  
				ret="0"  
			else:  
				ret="1"                      
		return Response({"status":ret, "info":"", "data":new_name})


class UploadImage(APIView):
	def get(self, request, format=None):
		longitude = (float)(request.query_params["longitude"])
		latitude = (float)(request.query_params["latitude"])
		path = request.query_params["path"]
		# user = request.user
		user = User.objects.get(username='test1')

		face_info=getFaceInfo(path)
		faces=face_info['faces']
		age=0
		male=0
		female=0
		attractive=0
		for face in faces:
			attributes=face['attributes']
			age+=attributes['age']
			gender=int(attributes['gender'])
			if gender<50:
				female=female+1
			else:
				male=male+1
			attractive+=attributes['attractive']
		faces_num=len(faces)
		if faces_num>0:
			age=float(age)/faces_num
			gender=float(gender)/faces_num
			attractive=float(attractive)/faces_num
		else:
			age=-1
			male=-1
			female=-1
			attractive=-1
		print age
		print male
		print female
		print attractive

		img = Image()
		img.created = timezone.now()
		img.creator = user
		img.path = path
		img.latitude = latitude
		img.longitude = longitude
		img.age=age
		img.male=male
		img.female=female
		img.attractive=attractive
		img.save()
		serializer = ImageSerializer(img)
		return Response({"status":0, "info":"", "data":face_info['faces']})


class GetImageList(APIView):
	def get(self, request, format=None):
		img_list = Image.objects.all()
		serializer = ImageSerializer(img_list,many=True)
		return Response({"status":0, "info":"", "data":serializer.data})



class GetAroundImageList(APIView):
	def get(self, request, format=None):
		longitude = (float)(request.query_params["longitude"])
		latitude = (float)(request.query_params["latitude"])

		img_list = Image.objects.filter(longitude__gte=longitude-0.003,longitude__lte=longitude+0.003,latitude__gte=latitude-0.003,latitude__lte=latitude+0.003)
		serializer = ImageSerializer(img_list,many=True)

		analyzer = {}
		age=0
		male=0
		female=0
		attractive=0
		for img in img_list:
			if img.age==-1:
				continue
			age+=img.age
			attractive+=img.attractive
			male+=img.male
			female+=img.female
		if len(img_list)>0:
			age=float(age)/len(img_list)
			age=float(attractive)/len(img_list)

		comment_list = Comment.objects.filter(img__in=img_list)
		attitude=0
		words=""
		for comment in comment_list:
			attitude+=comment.attitude
			words+=comment.content
		if len(comment_list)>0:
			attitude=float(attitude)/len(comment_list)
			words=getKeyWords(words)

		analyzer['age']=age
		analyzer['attractive']=attractive
		analyzer['male']=male
		analyzer['female']=female
		analyzer['attitude']=(attitude+1)*5
		analyzer['tag']=words

		return Response({"status":0, "info":"", "total":analyzer, "data":serializer.data})



class AddComment(APIView):
	def get(self, request, pk):
		content = request.query_params["content"]
		attitude_list=list(getAttitude(content))
		attitude_list=attitude_list[0]
		# print attitude_list
		# print attitude_list[0]
		attitude = float(attitude_list[0])-float(attitude_list[1])
		img = Image.objects.get(pk=pk)
		# user = request.user
		user = User.objects.get(username='test1')

		comment = Comment()
		comment.created = timezone.now()
		comment.user = user
		comment.img = img
		comment.content = content
		comment.attitude = attitude
		comment.save()
		serializer = CommentSerializer(comment)
		return Response({"status":0, "info":"", "data":serializer.data})


class GetCommentPerImg(APIView):
	def get(self, request, pk):
		img = Image.objects.get(pk=pk)
		comment = Comment.objects.filter(img=img)
		serializer = CommentSerializer(comment,many=True)
		return Response({"status":0, "info":"", "data":serializer.data})



class ImgLike(APIView):
	def get(self, request, pk):
		user = User.objects.get(username='test1')
		img = Image.objects.get(pk=pk)
		if request.user.liked_image.filter(id=img.id).exists():
			return Response({"status":1, "info":"已经赞过啦", "data":""})
		img.likes.add(user)
		return Response({"status":0, "info":"success", "data":""})	
	
class ImgUnlike(APIView):
	def get(self, request, pk):
		user = User.objects.get(username='test1')
		img = Image.objects.get(pk=pk)
		img.likes.remove(user)
		return Response({"status":0, "info":"success", "data":""})	


class CommentLike(APIView):
	def get(self, request, pk):
		user = User.objects.get(username='test1')
		comment = Comment.objects.get(pk=pk)
		if request.user.liked_comment.filter(id=comment.id).exists():
			return Response({"status":1, "info":"已经赞过啦", "data":""})
		comment.likes.add(user)
		return Response({"status":0, "info":"success", "data":""})	
	
class CommentUnlike(APIView):
	def get(self, request, pk):
		user = User.objects.get(username='test1')
		comment = Comment.objects.get(pk=pk)
		comment.likes.remove(user)
		return Response({"status":0, "info":"success", "data":""})	








