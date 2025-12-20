FROM nginx:alpine

# Удаляем дефолтный конфиг
RUN rm /etc/nginx/conf.d/default.conf

# Копируем nginx конфиг
COPY conf.d/nginx.conf /etc/nginx/conf.d/default.conf

# Копируем SSL сертификаты
COPY certs /etc/nginx/certs

# Открываем порты
EXPOSE 80 443

# Запуск nginx
CMD ["nginx", "-g", "daemon off;"]