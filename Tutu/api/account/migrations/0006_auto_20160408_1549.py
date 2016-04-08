# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('account', '0005_auto_20160116_1424'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='accounttoken',
            name='user',
        ),
        migrations.DeleteModel(
            name='AccountToken',
        ),
    ]
