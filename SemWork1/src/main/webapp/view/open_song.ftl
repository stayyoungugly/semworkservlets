<#-- @ftlvariable name="user" type="com.itis.servlets.dto.out.UserDto" -->
<#-- @ftlvariable name="playlist" type="com.itis.servlets.dto.out.PlaylistDto" -->
<#-- @ftlvariable name="song" type="com.itis.servlets.dto.out.SongDto" -->
<#-- @ftlvariable name="songs" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="playlists_in_library" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="playlists" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="songs_in_library" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="posts" type="java.util.List<com.itis.servlets.dto.out.PostDto"> -->

<html lang="en">
<head>
    <title>Трек</title>
    <link href="/css/open_song.css" rel="stylesheet">
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
    <h2>Трек</h2>
    <div class="playlist-info">
        <#if song.getCoverId()??>
            <img class="album-cover" alt="IMAGE" src="/files/${song.getCoverId()}"/>
        <#else>
            <img class="album-cover" alt="IMAGE" src="/nocover.jpg"/>
        </#if>
        <div class="text-info">
            <p><b>Название:</b> ${song.getTitle()}</p>
            <p><b>Исполнитель:</b> ${song.getCreator()}</p>
            <p><b>Описание:</b> ${song.getDescription()}</p>
            <p><b>Создан:</b> ${song.getCreatedAt()}</p>
        </div>
    </div>
    <div class="list">
        <audio class="player" controls="controls" preload="auto">
            <source src="/files/${song.getSongId()}" type="audio/mpeg">
        </audio>
    </div>
    <form enctype="multipart/form-data" method="post" action="/open-song?song_id=${song.id}">
        <#if user?? && user.getId() = song.getAuthor().getId()>
            <label>Поделиться
                <input type="checkbox" name="checkbox" <#if song.isShared>checked="checked"</#if>/>
            </label>
            <input type="checkbox" name="helper" class="helper" checked="checked">
        </#if>
        <div class="submit-buttons">
            <input type="submit" class="submit-button" name="close" value="Закрыть">
            <input type="submit"
                   class="delete-button" <#if user?? && user.getId() = song.getAuthor().getId()> name="delete" value="Удалить трек" <#else> name="add" value="Добавить к себе"</#if> />
        </div>
    </form>
</div>
</body>
</html>