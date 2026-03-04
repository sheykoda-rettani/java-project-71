# Проект "Вычислитель отличий"
[![Actions Status](https://github.com/sheykoda-rettani/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/sheykoda-rettani/java-project-71/actions) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sheykoda-rettani_java-project-71&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sheykoda-rettani_java-project-71)  [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=sheykoda-rettani_java-project-71&metric=bugs)](https://sonarcloud.io/summary/new_code?id=sheykoda-rettani_java-project-71)
<br>Проект представляет собой утилиту на Java для сравнения двух конфигурационных файлов и отображения различий между ними. Утилита поддерживает различные форматы входных файлов (JSON, YAML) и позволяет выводить различия в удобочитаемом виде в трёх форматах: _stylish_, _plain_ и _json_
## Особенности

- Поддерживает сравнение JSON и YAML файлов.
- Возможности выбора одного из трех видов представления результата: _stylish_ (красивый стиль), _plain_ (упрощённое представление), _json_ (форматированный JSON).

## Установка и запуск проекта
Прежде всего установите утилиту `make`. Затем соберите проект следующей командой:
```shell
  make build 
```
После этого выполните команду
```shell
  ./build/install/app/bin/app <путь к файлу 1> <путь к файлу 2>
```
Пример запуска
```shell
  ./build/install/app/bin/app D:\diff\before_1.json D:\diff\after_1.json
```
Посмотреть подробную информацию об использовании программы можнно запустив ее c аргументом -h
```shell
  ./build/install/app/bin/app -h
```