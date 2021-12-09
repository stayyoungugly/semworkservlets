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
    <title>Плейлист</title>
    <link href="/css/open_playlist.css" rel="stylesheet">
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
    <h2>Плейлист</h2>
    <div class="playlist-info">
        <#if playlist.getCoverId()??>
            <img class="album-cover" alt="IMAGE" src="/files/${playlist.getCoverId()}"/>
        <#else>
            <img class="album-cover" alt="IMAGE" src="../nocover.jpg"/>
        </#if>
        <div class="text-info">
            <p><b>Название:</b> ${playlist.getTitle()}</p>
            <p><b>Описание:</b> ${playlist.getDescription()}</p>
            <p><b>Создан:</b> ${playlist.getCreatedAt()}</p>
        </div>
    </div>
    <div class="demo">
        <input class="hide" id="hd-1" type="checkbox">
        <label for="hd-1">Всего треков: ${playlist.songs?size}</label>
        <div class="list">
            <#if playlist.songs??>
                <#list playlist.songs as song>
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
    <form enctype="multipart/form-data" method="post" action="/open-playlist?playlist_id=${playlist.id}">
        <#if user?? && user.getId() == playlist.getAuthor().getId()>
            <label>Поделиться
                <input type="checkbox" name="checkbox" <#if playlist.isShared>checked="checked"</#if>/>
            </label>
            <input type="checkbox" class="helper" name="helper" checked="checked">
        </#if>
        <div class="submit-buttons">
            <input type="submit" class="submit-button" name="close" value="Закрыть">
            <input type="submit" class="delete-button"
                   <#if user?? && user.getId() == playlist.getAuthor().getId()>name="delete" value="Удалить плейлист"
                   <#else>name="add" value="Добавить к себе"</#if>
        </div>
    </form>
</div>
</body>
</html>