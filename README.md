Before starting a program please start the database by opening terminal from toplevel folder (where Dockerfile is) and running:

docker build -t test-db . && docker run -d -p 5432:5432 test-db

If docker doesn't run check that port 5432 is available 

--

### Contents

* Chapter 2: basic setup with postgres/h2 database
* Chapter 3: Pooling / Basic HQL
