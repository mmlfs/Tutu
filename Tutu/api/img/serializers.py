
from django.contrib.auth.models import User

from rest_framework import serializers

from Tutu.api.img.models import Image,Comment




class CommentSerializer(serializers.ModelSerializer):
    # user = UserBriefSerializer()
    likes_count = serializers.IntegerField(source='likes.count')    
    class Meta:
        model = Comment
        fields = ('id', 'user', 'attitude', 'img', 'created', 'likes_count', 'content')

class ImageSerializer(serializers.ModelSerializer):
    related_img = CommentSerializer(serializers.PrimaryKeyRelatedField(many=True, required=False, read_only=True),many=True)
    likes_count = serializers.IntegerField(source='likes.count')
    # comments_count = serializers.IntegerField(source='comments.count')
    class Meta:
        model = Image
        fields = ('id', 'creator', 'latitude', 'longitude', 'created', 'likes_count', 'path', 'male', 'age', 'attractive', 'female', 'related_img')
