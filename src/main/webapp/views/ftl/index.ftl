<#import "taglib/spring.ftl" as spring>
<!DOCTYPE html>
<html>
	<head>
		<title><@spring.message "app.name" /> - <@spring.message "app.description" /></title>
		<link type="text/css" rel="stylesheet" href="${contextPath}/static/assets/bootstrap/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="${contextPath}/static/static/css/base.css">
        <link type="text/css" rel="stylesheet" href="${contextPath}/static/static/css/common.css">
        <link type="text/css" rel="stylesheet" href="${contextPath}/static/static/css/zk.css">
	</head>
	<body>
        <div class="container-full">
            <div class=" col-lg-12 center-block masthead">
                <h1><@spring.message "app.name" /></h1>
                <p><@spring.message "app.description" /></p>
                <div class="clearfix"></div>
                <form class="col-lg-12" action="${contextPath}/zkRead/connect" method="get">
                    <div class="input-group input-group-lg col-sm-offset-4 col-sm-4">
                        <input type="text" name="zkServer" required class="center-block form-control input-lg" title="" placeholder="<@spring.message 'app.zk.zkserver.placeholder' />">
                  	    <span class="input-group-btn"><button class="btn btn-lg btn-primary" type="submit"><@spring.message "app.common.btn.go" /></button></span>
                    </div>
                </form>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="container">
            <div class="col-lg-12 content">
                <#if zkServers??>
		            <#list zkServers?keys as key>
		                <div class="col-md-4">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <a href="${contextPath}/zkRead/connect?zkServer=${zkServers[key]!''}"><h3>${key!''}</h3></a>
                                </div>
                                <div class="panel-body wrap">${zkServers[key]!''}</div>
                            </div>
                        </div>
		            </#list>
		        </#if>
            </div>
        </div>
        <script type="text/javascript" src="${contextPath}/static/assets/jquery/jquery.min.js"></script>
	</body>
</html>