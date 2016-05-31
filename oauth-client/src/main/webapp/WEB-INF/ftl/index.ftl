<!DOCTYPE html>
<html>
    <head>
        <title>首页</title>
    </head>
    <body>
        <form action="/client/oauth/callback" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit" class="btn">登录</button>
        </form>
    </body>
</html>