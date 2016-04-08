#coding=utf-8

from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.authtoken.models import Token
from django.utils import timezone

from django.contrib import auth
from django.contrib.auth.models import User
from Tutu.api.img.models import Image,Comment
from Tutu.api.img.serializers import ImageSerializer,CommentSerializer
# from bosonnlp import BosonNLP

import urllib2
import urllib
import httplib 
import json
from urllib import quote
from urllib import urlencode


def getKeyWords(command):
	nlp = BosonNLP("IrtCRUKX.4360.giOuq49VR3V-")
	r = nlp.extract_keywords(command, top_k=3)
	l = []
	for k,v in r:
		v = v.encode('utf8')
		l.append(v)
	return l

class UploadImage(APIView):
	def get(self, request, format=None):
		longitude = (float)(request.query_params["longitude"])
		latitude = (float)(request.query_params["latitude"])
		path = request.query_params["path"]
		user = request.user

		img = Image()
		img.created = timezone.now()
		img.creator = user
		img.path = path
		img.latitude = latitude
		img.longitude = longitude
		img.save()
		serializer = ImageSerializer(img)
		return Response({"status":0, "info":"", "data":serializer.data})


class GetImageList(APIView):
	def get(self, request, format=None):
		img_list = Image.objects.all()
		serializer = ImageSerializer(img_list,many=True)
		return Response({"status":0, "info":"", "data":serializer.data})


class AddComment(APIView):
	def get(self, request, pk):
		content = request.query_params["content"]
		img = Image.objects.get(pk=pk)
		user = request.user

		comment = Comment()
		comment.created = timezone.now()
		comment.user = user
		comment.img = img
		comment.content = content
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
		img = Image.objects.get(pk=pk)
		if request.user.liked_image.filter(id=img.id).exists():
			return Response({"status":1, "info":"已经赞过啦", "data":""})
		img.likes.add(request.user)
		return Response({"status":0, "info":"success", "data":""})	
	
class ImgUnlike(APIView):
	def get(self, request, pk):
		img = Image.objects.get(pk=pk)
		img.likes.remove(request.user)
		return Response({"status":0, "info":"success", "data":""})	


class CommentLike(APIView):
	def get(self, request, pk):
		comment = Comment.objects.get(pk=pk)
		if request.user.liked_comment.filter(id=comment.id).exists():
			return Response({"status":1, "info":"已经赞过啦", "data":""})
		comment.likes.add(request.user)
		return Response({"status":0, "info":"success", "data":""})	
	
class CommentUnlike(APIView):
	def get(self, request, pk):
		comment = Comment.objects.get(pk=pk)
		comment.likes.remove(request.user)
		return Response({"status":0, "info":"success", "data":""})	








