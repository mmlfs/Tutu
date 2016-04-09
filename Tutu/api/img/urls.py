from django.conf.urls import include, url
from django.contrib import admin
from Tutu.api.img import views

urlpatterns = [
    # Examples:
    # url(r'^$', 'TripFM.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    url(r"^test/$", views.Test.as_view(), name="test"),
    url(r"^upload/$", views.UploadImage.as_view(), name="upload_image"),
    url(r"^list_images/$", views.GetImageList.as_view(), name="get_imageslist"),
    url(r"^(?P<pk>\d+)/add_comment/$", views.AddComment.as_view(), name="add_comment"),
    url(r"^(?P<pk>\d+)/list_comment/$", views.GetCommentPerImg.as_view(), name="list_comments"),

    url(r"^(?P<pk>\d+)/image_like/$", views.ImgLike.as_view(), name="image_like"),
    url(r"^(?P<pk>\d+)/image_unlike/$", views.ImgUnlike.as_view(), name="image_unlike"),
   	url(r"^(?P<pk>\d+)/comment_like/$", views.CommentLike.as_view(), name="comment_like"),
    url(r"^(?P<pk>\d+)/comment_unlike/$", views.CommentUnlike.as_view(), name="comment_unlike"),
]
