# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('img', '0002_auto_20160409_0407'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='image',
            name='gender',
        ),
        migrations.AddField(
            model_name='image',
            name='female',
            field=models.IntegerField(default=-1, null=True, blank=True),
        ),
        migrations.AddField(
            model_name='image',
            name='male',
            field=models.IntegerField(default=-1, null=True, blank=True),
        ),
    ]
