# 版本1

###	article 表	###
aid				主键(int)
title			文章标题(200)
content			内容(text)
tags			标签(64)  逗号分隔
categoryId	    分类(64)  非null ""就是默认分类
authorId		作者(32)
status			状态(默认保存为草稿0	保存发布1)(1)
created			创建时间(datetime)
modified		修改时间(datetime)

###	category ###
ctid			主键(int)
name			名称(64)
authorId		作者(32)

### 后台接口 ###
文章列表			get		/admin/article 
发布文章			post	/admin/article/publish
修改文章			post	/admin/article/modify
编辑文章			get		/admin/article/{id}
删除文章			post	/admin/article/delete

分类列表			get		/admin/category
添加分类			post	/admin/category/add
修改分类			post	/admin/category/edit
编辑分类			get		/admin/category/{id}
删除分类			post	/admin/category/delete
