<html>
<head>
    <meta charset="utf-8">
    <title>提示</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/sell/css/style.css">
    <link href="https://cdn.bootcss.com/bootstrap-fileinput/4.4.7/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
</head>
<body>
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="alert alert-dismissable alert-success">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4>${msg}</h4>
                        <a href="${url}" class="alert-link"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script>
    setTimeout('location.href="${url}"');
</script>
</html>