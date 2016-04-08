# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Comment',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created', models.DateTimeField(auto_now_add=True)),
                ('attitude', models.FloatField(default=0, null=True, blank=True)),
                ('content', models.CharField(max_length=2000, null=True, blank=True)),
            ],
        ),
        migrations.CreateModel(
            name='Image',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('created', models.DateTimeField(auto_now_add=True)),
                ('longitude', models.FloatField(default=0, null=True, blank=True)),
                ('latitude', models.FloatField(default=0, null=True, blank=True)),
                ('path', models.CharField(max_length=2000, null=True, blank=True)),
                ('creator', models.ForeignKey(related_name='created_image', to=settings.AUTH_USER_MODEL)),
                ('likes', models.ManyToManyField(related_name='liked_image', to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.AddField(
            model_name='comment',
            name='img',
            field=models.ForeignKey(related_name='related_img', to='img.Image'),
        ),
        migrations.AddField(
            model_name='comment',
            name='likes',
            field=models.ManyToManyField(related_name='liked_comment', to=settings.AUTH_USER_MODEL),
        ),
        migrations.AddField(
            model_name='comment',
            name='user',
            field=models.ForeignKey(related_name='created_comment', to=settings.AUTH_USER_MODEL),
        ),
    ]
