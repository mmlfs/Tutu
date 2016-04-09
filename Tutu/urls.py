from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    # Examples:
    # url(r'^$', 'TripFM.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r"^api/", include("Tutu.api.urls")),
    url(r"", include("Tutu.web.urls")),
    url(r'^download/(?P<path>.*)$', 'django.views.static.serve', {'document_root': '/home/leebaok/Project/hackpku/Tutu/Tutu/img/upload/', 'show_indexes':True})
]
