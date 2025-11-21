FROM node:20-alpine

WORKDIR /app

# Install build dependencies for native modules
RUN apk add --no-cache python3 make g++

COPY client/package.json ./
# Copy lock file if exists, otherwise npm install will generate one
# COPY client/package-lock.json ./ 

RUN npm install

COPY client/ .

RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]
