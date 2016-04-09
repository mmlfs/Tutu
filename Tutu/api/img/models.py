from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class Image(models.Model):
	created = models.DateTimeField(auto_now_add=True)
	longitude = models.FloatField(default=0, null=True, blank=True)
	latitude = models.FloatField(default=0, null=True, blank=True)
	likes = models.ManyToManyField(User, related_name='liked_image')
	creator = models.ForeignKey(User, related_name='created_image')
	path = models.CharField(max_length=2000, null=True, blank=True)
	male = models.IntegerField(default=-1, null=True, blank=True)
	female = models.IntegerField(default=-1, null=True, blank=True)
	age = models.FloatField(default=-1, null=True, blank=True)
	attractive = models.FloatField(default=-1, null=True, blank=True)




class Comment(models.Model):
	created = models.DateTimeField(auto_now_add=True)
	attitude = models.FloatField(default=0, null=True, blank=True)
	user = models.ForeignKey(User, related_name='created_comment')
	likes = models.ManyToManyField(User, related_name='liked_comment')
	img = models.ForeignKey(Image, related_name='related_img')
	content = models.CharField(max_length=2000, null=True, blank=True)