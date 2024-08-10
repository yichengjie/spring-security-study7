1. 启动mysql
    ```text
    docker run --name mysql -p 3306:3306 -v /home/yicj/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:latest
    ```
2. 