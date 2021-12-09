<html lang="en">
<head>
    <title>Регистрация</title>
    <link href="/css/sign_up.css" rel="stylesheet">
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
    <div class="signup_form">
        <form id="auth" action="/sign-in" method="GET">
            <input type="submit" value="Уже есть аккаунт? Войти" class="auth-button">
        </form>
        <form action="/sign-up" method="post" enctype="multipart/form-data">
            <label>Имя</label>
            <input name="firstName" type="text" placeholder="Введите имя"/>
            <label>Фамилия</label>
            <input name="lastName" type="text" placeholder="Введите фамилию"/>
            <label>Возраст</label>
            <input name="age" type="number" placeholder="Введите возраст" min="3" max="100"/>
            <label>Почта</label>
            <input name="email" type="email" placeholder="Введите почту"/>
            <label>Пароль</label>
            <input name="password" type="password" placeholder="Введите пароль"/>
            <label>Загрузить аватарку (необязательно)</label>
            <input name="file" type="file" accept=".jpg, .png, .jpeg">
            <input class="submit-button" type="submit" value="Сохранить и войти">
        </form>
    </div>
    <h1>${message}</h1>
</div>
</body>
</html>
