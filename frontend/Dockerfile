# Use the official Nginx image as the base image
FROM nginx:1.21.1-alpine

# Remove the default Nginx configuration file
RUN rm /etc/nginx/conf.d/default.conf

# Copy your Nginx configuration file to the container
COPY nginx.conf /etc/nginx/conf.d/

# Copy the build directory to the container
COPY build /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
