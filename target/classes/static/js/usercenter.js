layui.use(['jquery','layer','form','table',"laydate"],function(){
	var form = layui.form,
		$ = layui.jquery,
		laydate = layui.laydate,
		layer = layui.layer;
	
	  laydate.render({
	    elem: '#birthdate'
	  });
		
	$("#submitForm").on('click', function() {
		
		var params = {}
		
		 var nickName = $("#nickName").val().trim();
		 var descript = $("#descript").val().trim();
		 var birthdate = $("#birthdate").val().trim();
		 var country = $("#country").val().trim();
		 var nation = $("#nation").val().trim();
		 var censusregister = $("#censusregister").val().trim();
		 var school = $("#school").val().trim();
		 var major = $("#major").val().trim();
		 var politicallandscape = $("#politicallandscape").val().trim();
		 var skills = [];
		 $("#skills>input:checked").each(function(){
			 skills.push($(this).attr("name"));
		 })
		 if( nickName){
			 params['nickName']=nickName
		 }
		 if( sex){
			 params['sex']=sex
		 }
		 if( descript){
			 params['descript']=descript
		 }
		 if( birthdate){
			 params['birthdate']=birthdate
		 }
		 if( country){
			 params['country']=country
		 }
		 if( nation){
			 params['nation']=nation
		 }
		 if( censusregister){
			 params['censusregister']=censusregister
		 }
		 if( school){
			 params['school']=school
		 }
		 if( major){
			 params['major']=major
		 }
		 if( politicallandscape){
			 params['politicallandscape']=politicallandscape
		 }
		 if(skills.length){
			 params['skills']= skills.join(" ")
		 }
		 params['sex'] = $("#sex>input:checked").eq(0).val();
		Utils.post(basePath+"/changeUser",params,function(result){
			 if(result.code){
     			return layer.alert(result.message);
     		}
     		layer.alert(result.message, {
 				closeBtn : 0
 			}, function(index) {
 				window.location.reload();
 			});
			 
		 })
		
		 
		 return false;
	 });
    
});