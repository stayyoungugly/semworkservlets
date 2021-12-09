<#-- @ftlvariable name="user" type="com.itis.servlets.dto.out.UserDto" -->
<#-- @ftlvariable name="songs" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="playlists_in_library" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="playlists" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="songs_in_library" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="posts" type="java.util.List<com.itis.servlets.dto.out.PostDto"> -->

<html lang="en">
<head>
    <title>Создать плейлист</title>
    <link href="/css/add_playlist.css" rel="stylesheet">
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
        <h2><#if message??>${message}<#else>Новый плейлист</#if> </h2>
        <form action="/add-playlist" method="post" enctype="multipart/form-data">
            <label>Название</label>
            <input name="title" type="text" placeholder="Введите название"/>
            <label>Описание</label>
            <input name="description" type="text" placeholder="Введите описание (необязательно)"/>
            <label>Загрузить обложку (необязательно)</label>
            <input name="cover_file" type="file" accept=".jpg, .png, .jpeg">
            <div class="demo">
                <input class="hide" id="hd-1" type="checkbox">
                <label for="hd-1">Добавить треки</label>
                <div class="list">
                    <#if songs??>
                        <#list songs as song>
                            <div class="song-info">
                                <#if song.getCoverId()??>
                                    <img class="song-cover" alt="IMAGE" src="/files/${song.getCoverId()}"/>
                                <#else>
                                    <img class="song-cover" alt="IMAGE" src="/nocover.jpg"/>
                                </#if>
                                <div class="song-sub-info">
                                    <div class="song-checkbox">
                                        <div class="song-title-creator">
                                            <div class="song-title">${song.title}</div>
                                            <div class="song-creator">${song.creator}</div>
                                        </div>
                                        <label>Добавить трек
                                            <input type="checkbox" name="checked" value="${song.getId()}"/>
                                        </label>
                                    </div>
                                    <audio class="player" controls="controls" preload="auto">
                                        <source src="/files/${song.getSongId()}" type="audio/mpeg">
                                    </audio>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
            <label>Поделиться</label>
            <input name="isShared" type="checkbox" placeholder="Поделиться треком"/>
            <input class="submit-button" type="submit" value="Создать">
        </form>
    </div>
</div>
</body>
</html>