# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('img', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='image',
            name='age',
            field=models.FloatField(default=-1, null=True, blank=True),
        ),
        migrations.AddField(
            model_name='image',
            name='attractive',
            field=models.FloatField(default=-1, null=True, blank=True),
        ),
        migrations.AddField(
            model_name='image',
            name='gender',
            field=models.FloatField(default=-1, null=True, blank=True),
        ),
    ]
