# Screen Definitions (화면 정의서)

This document defines the User Interface (UI) and User Experience (UX) for the Market Place Client (Next.js).
The design should follow a **Modern, Premium, Dark-Themed** aesthetic (Glassmorphism, Neon accents).

## 1. Common Layout
- **Header**: Logo (Left), Navigation (Exchange, Currency, Community), Search Bar (Center), User Menu/Login (Right).
- **Footer**: Copyright, Links, Social Icons.
- **Sidebar (Optional)**: Quick categories or user shortcuts on specific pages.

## 2. Authentication
### 2.1 Login Page (`/login`)
- **Layout**: Centered card on a blurred background.
- **Elements**:
    - Email Input
    - Password Input
    - "Login" Button (Primary Color)
    - "Sign Up" Link
- **API**: `POST /api/v1/auth/login`

### 2.2 Signup Page (`/signup`)
- **Layout**: Similar to Login.
- **Elements**:
    - Email, Password, Confirm Password, Nickname.
    - "Register" Button.
- **API**: `POST /api/v1/auth/signup`

## 3. Main Dashboard (`/`)
- **Layout**:
    - **Hero Section**: Banner promoting featured game items or events.
    - **Currency Ticker**: Horizontal scrolling ticker showing current exchange rates (Gold/Silver/Copper).
    - **Recent Trades**: Grid of recently listed items.
    - **Community Highlights**: Top 3 popular posts.
- **Data**:
    - `GET /api/v1/trades?sort=createdAt,desc` (Limit 8)
    - `GET /api/v1/currency` (Summary)
    - `GET /api/v1/posts?sort=viewCount,desc` (Limit 3)

## 4. Exchange (Item Trading)
### 4.1 Item List (`/exchange`)
- **Layout**:
    - **Filter Sidebar**: Game Category, Price Range, Item Type (Weapon, Armor, etc.).
    - **Item Grid**: Cards showing Item Image, Name, Price, Seller Name, Time.
- **Interaction**: Click card -> Go to Detail.
- **API**: `GET /api/v1/trades`

### 4.2 Item Detail (`/exchange/[id]`)
- **Layout**:
    - **Left**: Large Item Image (Carousel if multiple).
    - **Right**:
        - Item Name, Rarity (Color coded), Price.
        - Seller Info (Rating, Nickname).
        - "Buy Now" Button (Triggers Transaction).
        - "Chat with Seller" Button (Opens Chat).
    - **Bottom**: Item Description, Stats.
- **API**:
    - `GET /api/v1/trades/{id}`
    - `POST /api/v1/trades/{id}/request` (Request Trade)
    - `POST /api/v1/trades/{id}/accept` (Seller accepts -> Creates Chat)

### 4.3 Trade Receipt (Completed Trade)
- **Layout**:
    - **Header**: "Trade Completed".
    - **Info**: Item Details, Final Price, Date.
    - **Parties**: Seller & Buyer IDs.
    - **Chat History**: Button to view archived chat log (loaded from file).
- **API**:
    - `GET /api/v1/trades/{id}/receipt`
    - `GET /api/v1/trades/{id}/chat-archive`

### 4.3 Register Item (`/exchange/new`)
- **Layout**: Form with Image Upload (Drag & Drop).
- **Elements**: Name, Category, Price, Description, Image Upload Area.
- **API**:
    - `POST /api/v1/files` (Upload images first)
    - `POST /api/v1/trades` (Submit trade data with file URLs)

## 5. Currency Exchange
### 5.1 Currency List (`/currency`)
- **Layout**: Table view of Buy/Sell offers.
- **Columns**: User, Amount, Rate (Price per Unit), Total Price, Action (Buy/Sell).
- **API**: `GET /api/v1/currency`

## 6. Community
### 6.1 Post List (`/community`)
- **Layout**: List of posts.
- **Elements**: Title, Author, Date, View Count, Comment Count.
- **API**: `GET /api/v1/posts`

### 6.2 Post Detail (`/community/[id]`)
- **Layout**:
    - Post Content (Title, Body, Author).
    - **Comments Section**:
        - Comment Input.
        - List of Comments (Indented for Replies).
- **API**:
    - `GET /api/v1/posts/{id}`
    - `GET /api/v1/posts/{id}/comments`
    - `POST /api/v1/posts/{id}/comments`

## 7. Chat
### 7.1 Chat Room (`/chat/[roomId]`)
- **Layout**:
    - **Sidebar**: List of active chat rooms (User Avatar, Last Message).
    - **Main Area**: Message History (Bubbles), Input Area.
- **Tech**: WebSocket (STOMP) over `/ws-chat`.
- **API**:
    - `GET /api/v1/chat/rooms` (Sidebar)
    - `GET /api/v1/chat/rooms/{roomId}/messages` (History)
    - WS `SEND /app/chat.sendMessage`
    - **Header**: Partner Name, "Trade Complete" Button.
    - **Main Area**: Message History (Bubbles), Input Area.
- **Interaction**:
    - "Trade Complete" clicked by both -> Trade Finalized -> Room Deleted -> Archived.
- **Tech**: WebSocket (STOMP) over `/ws-chat`.
- **API**:
    - `GET /api/v1/chat/rooms` (Sidebar)
    - `GET /api/v1/chat/rooms/{roomId}/messages` (History)
    - `POST /api/v1/chat/rooms/{roomId}/complete` (Mark as complete)
    - WS `SEND /app/chat.sendMessage`
    - WS `SUBSCRIBE /topic/room/{roomId}`
