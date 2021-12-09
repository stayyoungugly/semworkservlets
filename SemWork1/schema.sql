-- auto-generated definition
create table file_info
(
    id                 serial
        constraint file_info_pkey
            primary key,
    original_file_name varchar(100),
    storage_file_name  varchar(100) not null,
    size               bigint       not null,
    type               varchar(100)
);

alter table file_info
    owner to postgres;

-- auto-generated definition
create table users
(
    id            serial
        constraint users_pkey
            primary key,
    first_name    varchar(20),
    last_name     varchar(20),
    age           integer,
    password_hash varchar(100) not null,
    email         varchar(30)  not null
        constraint users_email_key
            unique,
    avatar_id     integer
        constraint users_avatar_id_fkey
            references file_info
            on delete set null
);

alter table users
    owner to postgres;


create table comments
(
    id         serial
        constraint comments_pkey
            primary key,
    author_id  integer       not null
        constraint comments_author_id_fkey
            references users,
    created_at timestamp     not null,
    content    varchar(1000) not null
);

-- auto-generated definition
create table playlists
(
    id          serial
        constraint playlists_pkey
            primary key,
    author_id   integer           not null
        constraint playlists_author_id_fkey
            references users
            on delete cascade,
    title       varchar(255)      not null,
    description varchar(5000)     not null,
    cover_id    integer
        constraint playlists_cover_id_fkey
            references file_info
            on delete set null,
    created_at  timestamp         not null,
    likes       integer default 0 not null,
    is_shared   boolean           not null
);

alter table playlists
    owner to postgres;

-- auto-generated definition
create table posts
(
    id         serial
        constraint posts_pkey
            primary key,
    author_id  integer       not null
        constraint posts_author_id_fkey
            references users
            on delete cascade,
    created_at timestamp     not null,
    content    varchar(1000) not null
);

alter table posts
    owner to postgres;

-- auto-generated definition
create table songs
(
    id          serial
        constraint songs_pkey
            primary key,
    author_id   integer           not null
        constraint songs_author_id_fkey
            references users
            on delete cascade,
    title       varchar(255)      not null,
    creator     varchar(255)      not null,
    description varchar(5000)     not null,
    created_at  timestamp         not null,
    cover_id    integer
        constraint songs_cover_id_fkey
            references file_info
            on delete set null,
    song_id     integer
        constraint songs_song_id_fkey
            references file_info
            on delete cascade,
    likes       integer default 0 not null,
    is_shared   boolean           not null
);

alter table songs
    owner to postgres;

-- auto-generated definition
create table songs_playlists
(
    song_id     integer not null
        constraint songs_playlists_song_id_fkey
            references songs
            on delete cascade,
    playlist_id integer not null
        constraint songs_playlists_playlist_id_fkey
            references playlists
            on delete cascade
);

alter table songs_playlists
    owner to postgres;

-- auto-generated definition
create table user_token
(
    user_id integer      not null
        constraint user_token_pkey
            primary key
        constraint user_token_user_id_fkey
            references users
            on delete cascade,
    token   varchar(200) not null
);

alter table user_token
    owner to postgres;

-- auto-generated definition
create table users_playlists
(
    user_id     integer not null
        constraint users_playlists_user_id_fkey
            references users
            on delete cascade,
    playlist_id integer not null
        constraint users_playlists_playlist_id_fkey
            references playlists
            on delete cascade,
    constraint users_playlists_pk
        primary key (user_id, playlist_id)
);

alter table users_playlists
    owner to postgres;

-- auto-generated definition
create table users_songs
(
    user_id integer not null
        constraint users_songs_user_id_fkey
            references users
            on delete cascade,
    song_id integer not null
        constraint users_songs_song_id_fkey
            references songs
            on delete cascade,
    constraint users_songs_pk
        primary key (user_id, song_id)
);

alter table users_songs
    owner to postgres;



