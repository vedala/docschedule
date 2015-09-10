
create user 'docdbowner'@'localhost' identified by 'docdbpwd';

grant all on docsched.* to docdbowner@localhost with grant option;