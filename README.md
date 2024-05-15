[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/B_Lemfbx)


kafka-console-consumer --topic game_updates --bootstrap-server broker:29092

Steps

1. cd my-app
2. docker build -t frontend .
3. cd ..
4. cd comp4651
5. docker build -t backend .
6. cd ..
7. docker compose up -d
8. Access frontend at http://localhost:3000