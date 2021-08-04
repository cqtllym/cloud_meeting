FROM nginx
COPY nginx.conf /etc/nginx/nginx.conf
COPY dist /dist
EXPOSE 22 80 443
ENTRYPOINT ["nginx", "-g", "daemon off;"]