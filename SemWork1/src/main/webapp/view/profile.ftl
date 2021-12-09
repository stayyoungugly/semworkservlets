<#-- @ftlvariable name="user" type="com.itis.servlets.dto.out.UserDto" -->
<#-- @ftlvariable name="songs" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="playlists_in_library" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="playlists" type="java.util.List<com.itis.servlets.dto.out.PlaylistDto>" -->
<#-- @ftlvariable name="songs_in_library" type="java.util.List<com.itis.servlets.dto.out.SongDto>" -->
<#-- @ftlvariable name="posts" type="java.util.List<com.itis.servlets.dto.out.PostDto"> -->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Моя страница</title>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="/css/profile.css" rel="stylesheet">

    <script src="/js/jquery.min.js" charset="UTF-8"></script>
    <script src="/js/profile_refresh.js" charset="UTF-8"></script>

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
    <div class="profile-info">
        <h1>Профиль</h1>
        <#if user.avatarId??>
            <img class="avatar" alt="IMAGE" src="/files/${user.getAvatarId()}"/>
        <#else>
            <img class="avatar" alt="IMAGE" src="/noavatar.png"/>
        </#if>
        <form action="/update-avatar" method="post" enctype="multipart/form-data" class="button_load">
            <input type="file" name="file" accept=".jpg, .png, .jpeg">
            <input type="submit" value="Загрузить" name="load">
        </form>

        <p><b>Имя:</b> ${user.getFirstName()}
        </p>
        <p><b>Фамилия:</b> ${user.getLastName()}
        </p>
        <p><b>Возраст:</b> ${user.getAge()}
        </p>
        <p><b>Почта:</b> ${user.getEmail()}
        </p>
        <form action="/sign-out" method="get">
            <input type="submit" value="Выйти" name="exit">
        </form>
    </div>
    <div class="tabs">
        <input type="radio" name="inset" value="" id="tab_1">
        <label for="tab_1">Мои треки</label>

        <input type="radio" name="inset" value="" id="tab_2">
        <label for="tab_2">Мои плейлисты</label>

        <input type="radio" name="inset" value="" id="tab_3">
        <label for="tab_3">Мои посты</label>
        <div id="txt_1">
            <h3>Загружено треков: <#if songs??>${songs?size} <#else> 0 </#if></h3>
            <a href="#zatemnenie">Загрузить новый трек</a>
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
                                <a class="open1" href="/open-song?song_id=${song.id}&current=profile">Подробнее</a>
                            </div>
                            <audio class="player" controls="controls" preload="">
                                <source src="/files/${song.getSongId()}" type="audio/mpeg">
                            </audio>
                        </div>
                    </div>
                </#list>
            </#if>
            <div id="zatemnenie">
                <div id="okno">
                    <div class="signup_form">
                        <h2>${message}</h2>
                        <form action="/add-song" method="post" enctype="multipart/form-data">
                            <label>Название</label>
                            <input name="title" type="text" placeholder="Введите название"/>
                            <label>Исполнитель</label>
                            <input name="creator" type="text" placeholder="Введите исполнителя"/>
                            <label>Описание</label>
                            <input name="description" type="text" placeholder="Введите описание (необязательно)"/>
                            <label>Загрузить обложку (необязательно)</label>
                            <input name="cover_file" type="file" accept=".jpg, .png, .jpeg">
                            <label>Загрузить трек (MP3)</label>
                            <input name="song_file" type="file" accept=".mp3">
                            <label>Поделиться</label>
                            <input name="isShared" type="checkbox" placeholder="Поделиться треком"/>
                            <input class="submit-button" type="submit" value="Загрузить">
                            <a href="#" class="close">Закрыть</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="txt_2">
            <h3>Создано плейлистов: <#if playlists??>${playlists?size} <#else> 0 </#if></h3>
            <a href="/add-playlist?songs=${user.getId()}">Создать новый плейлист</a>
            <#if playlists??>
                <#list playlists as playlist>
                    <div class="album-info">
                        <#if playlist.getCoverId()??>
                            <img class="album-cover" alt="IMAGE" src="/files/${playlist.getCoverId()}"/>
                        <#else>
                            <img class="album-cover" alt="IMAGE" src="../nocover.jpg"/>
                        </#if>
                        <div class="album-sub-info">
                            <div class="album-title">${playlist.title}</div>
                            <div class="album-creator">Треков: ${playlist.songs?size}</div>
                        </div>
                        <a class="open" href="/open-playlist?playlist_id=${playlist.id}&current=profile">Открыть</a>
                    </div>
                </#list>
            </#if>
        </div>
        <div id="txt_3">
            <div id="posts-list" class="posts-lists">
                <form id="add-post-form" action="/add-post" method="post">
                    <label>
                        Ваша запись:
                        <textarea id="content" class="input_green" required name="content"></textarea>
                    </label>
                    <input class="button1" value="Отправить" type="submit">
                </form>
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
