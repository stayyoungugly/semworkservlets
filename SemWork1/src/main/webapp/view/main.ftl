<#-- @ftlvariable name="user" type="com.itis.servlets.dto.out.UserDto" -->
<#-- @ftlvariable name="songs" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="playlists_in_library" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="playlists" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="songs_in_library" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="posts" type="java.util.List<com.itis.servlets.dto.out.PostDto"> -->

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Главная</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="/css/main.css" rel="stylesheet">
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
<h1>Общая стена</h1>
<div class="container">
    <div class="tabs">
        <input type="radio" name="inset" value="" id="tab_1">
        <label for="tab_1">Треки</label>

        <input type="radio" name="inset" value="" id="tab_2">
        <label for="tab_2">Плейлисты</label>

        <input type="radio" name="inset" value="" id="tab_3">
        <label for="tab_3">Посты</label>
        <div id="txt_1">
            <#if songs??>
                <#list songs as song>
                    <div class="song-info">
                        <#if song.getCoverId()??>
                            <img class="song-cover" alt="IMAGE" src="/files/${song.getCoverId()}"/>
                        <#else>
                            <img class="song-cover" alt="IMAGE" src=/nocover.jpg>
                        </#if>
                        <div class="song-sub-info">
                            <div class="song-link-info">
                                <div class="song-title-creator">
                                    <div class="song-title">${song.title}</div>
                                    <div class="song-creator">${song.creator}</div>
                                </div>
                                <a class="open1" href="/open-song?song_id=${song.id}&current=main">Подробнее</a>
                            </div>
                            <audio class="player" controls="controls" preload="">
                                <source src="/files/${song.getSongId()}" type="audio/mpeg">
                            </audio>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
        <div id="txt_2">
            <#if playlists??>
                <#list playlists as playlist>
                    <div class="album-info">
                        <#if playlist.getCoverId()??>
                            <img class="album-cover" alt="IMAGE" src="/files/${playlist.getCoverId()}"/>
                        <#else>
                            <img class="album-cover" alt="IMAGE" src="/nocover.jpg"/>
                        </#if>
                        <div class="album-sub-info">
                            <div class="album-title">${playlist.title}</div>
                            <div class="album-creator">
                                Автор: ${playlist.getAuthor().getFirstName()} ${playlist.getAuthor().getLastName()}</div>
                        </div>
                        <a class="open" href="/open-playlist?playlist_id=${playlist.id}&current=main">Открыть</a>
                    </div>
                </#list>
            </#if>
        </div>
        <div id="txt_3">
            <div id="posts-list" class="posts-lists">
                <div class="divider"></div>
                <div id="post-list">
                    <#list posts as post>
                        <div id="post${post.id}">
                            <div class="light_blue text">${post.createdAt?string("dd MMMM yyyy 'г.,' HH:mm")}</div>
                            <div class="text">
                                ${post.author.firstName ! " NO NAME"} ${post.author.lastName ! " NO NAME"} </div>
                            <div class="text">${post.content}</div>
                            <#if post_index < posts?size - 1>
                                <div class="divider"></div>
                            </#if>
                        </div>
                    </#list>
                    <div style="text-align: center">Всего <span id="postsCount">${posts?size}</span> записей</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
