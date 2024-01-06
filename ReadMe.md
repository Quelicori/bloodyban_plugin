# Плагин на бан после смерти

### Команды

**<span style="color:rgb(84,255,159)">/bloodyban exempt <ник>** - добавить в исключения (игрок в этом списке не карается за смерть)

![alt text](https://media.discordapp.net/attachments/966071844828512306/1193289951756959865/image.png?ex=65ac2cde&is=6599b7de&hm=77a3ede459851b532959fbf27a053195cb65cce29f1c24cf67203177c7f00f2a&=&format=webp&quality=lossless&width=1058&height=200)

**<span style="color:rgb(84,255,159)">/bloodyban unexempt <ник>** - убрать из исключения

![alt text](https://media.discordapp.net/attachments/966071844828512306/1193295197359050854/image.png?ex=65ac31c1&is=6599bcc1&hm=b6759f204a33d77d4aebfbdd3c78ed4bdd0bb130eb4d06c4b6a81c1758b4d761&=&format=webp&quality=lossless&width=1058&height=200)

**<span style="color:rgb(84,255,159)">/bloodyban reload** - перезагрузка плагина

### Конфигурация плагина

````yml
# config.yml

#  _____                                                     _____
# ( ___ )                                                   ( ___ )
#  |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   |
#  |   |  ____  _                 _       ____               |   |
#  |   | | __ )| | ___   ___   __| |_   _| __ )  __ _ _ __   |   |
#  |   | |  _ \| |/ _ \ / _ \ / _` | | | |  _ \ / _` | '_ \  |   |
#  |   | | |_) | | (_) | (_) | (_| | |_| | |_) | (_| | | | | |   |
#  |   | |____/|_|\___/ \___/ \__,_|\__, |____/ \__,_|_| |_| |   |
#  |   |                            |___/                    |   |
#  |___|~~~~~~~~~~~~~~~~~~~~~~~~Config~~~~~~~~~~~~~~~~~~~~~~~|___|
# (_____)                                                   (_____)


# Кастомная команда бана (по умолчанию tempban (CMI))
banCommand: tempban

# Время бана (Мин)
banDurationMinutes: 10

# Сообщение
banMessage: "Смерть"

#Тонкая настройка времени в тиках (время после смерти)
timeBefore: 10
````
