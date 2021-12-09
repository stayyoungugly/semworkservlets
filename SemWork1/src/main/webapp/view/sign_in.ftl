<html lang="en">
<head>
    <title>Авторизация</title>
    <link href="/css/sign_in.css" rel="stylesheet">
</head>
<body>
<header>
    <div class="inner">
        <div class="logo">
            <div>
                <img src="/logo.png" alt="Logo">
            </div>
        </div>
        <nav>
            <li><span><a href="/main"><b>Главная</b></a></span></li>
            <#if user??>
                <li><span><a href="/profile"><b>Профиль</b></a></span></li>
                <li><span><a href="/sign-out"><b>Выход</b></a></span></li>
            <#else>
                <li><span><a href="/sign-in"><b>Войти</b></a></span></li>
                <li><span><a href="/sign-up"><b>Регистрация</b></a></span></li>
            </#if>
        </nav>
    </div>
</header>
<div class="container">
    <h1>${message}</h1>
    <form action="/sign-in" method="post">
        <input name="email" type="email" placeholder="Email"/>
        <input name="password" type="password" placeholder="Password"/>
        <input type="submit" value="Enter">
    </form>
    <form id="auth" action="/sign-up" method="GET">
        <input type="submit" value="У вас нет аккаунта? Регистрация" class="auth-button">
    </form>
</div>
</body>
</html>
