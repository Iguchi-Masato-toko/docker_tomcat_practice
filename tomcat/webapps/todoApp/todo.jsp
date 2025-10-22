<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="bean.*" %>
<jsp:useBean id ="tdto" scope="request" class="bean.TodoDTO" />
<jsp:useBean id ="cdto" scope="request" class="bean.CategoryDTO" />
<%-- <% String msg = (String) request.getAttribute("msg");%> --%>
<jsp:useBean id ="msg" scope="request" class="java.lang.String" />
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>todoアプリ</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
  </head>
  <script>const statusArr = [
  <% for(int i = 0;i < cdto.size(); i++) { 
    CategoryBean cb = cdto.get(i);
    %>
    {
      id:<%= cb.getId() %>,
      name:"<%= cb.getCategoryName() %>"
      color: "<%= cb.getCategoryClass() %>"
    },
  <% } %>
  ];
  </script>
  <body class="bg-gray-100 min-h-screen">
    <header class="bg-blue-600 text-white py-4 shadow">
      <section class="container mx-auto px-4">
        <h1 class="text-3xl font-bold">todoアプリ</h1>
      </section>
    </header>
    <main class="container mx-auto px-4 mt-8">
      <section class="bg-white rounded-lg shadow p-6">
      <% if(msg != null && msg.length() != 0) { %>
        <p><%= msg %></p>
      <% } %>
        <h2 class="text-xl font-semibold mb-4">Todo</h2>
        <form action="/todoApp/todo" method="post" class="mb-6">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4 items-end">
            <!-- タスク内容 -->
            <div>
              <label for="task" class="block text-sm font-medium text-gray-700 mb-1">タスク内容</label>
              <input type="text" id="task" name="task" required
                    class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
            </div>

            <!-- カテゴリ -->
            <div>
              <label for="category" class="block text-sm font-medium text-gray-700 mb-1">カテゴリ番号</label>
              <% if(cdto.size() == 0) { %>
                <p class="text-gray-600">登録されているカテゴリはありません。</p>
              <% } %>
              <% if(cdto.size() != 0) { %>
              <select id="category" name="category" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
                <% for(int i = 0; i < cdto.size(); i++) {
                  CategoryBean cb = cdto.get(i);
                %>
                <option value="<%= cb.getId() %>"><%= cb.getCategoryName() %></option>
                <% } %>
              </select>
              <% } %>
            </div>

            <!-- 登録ボタン -->
            <div>
              <button type="submit" class="w-full bg-blue-600 text-white font-semibold py-2 px-4 rounded-md hover:bg-blue-700 hover:cursor-pointer transition">
                登録する
              </button>
            </div>
          </div>
        </form>

        <% if(tdto.size() != 0) {%>
          <div class="mt-6 mb-10">
            <h3 class="text-lg font-semibold mb-2">カテゴリで絞り込む</h3>
            <div id="filterArea" class="flex flex-wrap gap-4">
              <% for(int i = 0; i < cdto.size(); i++) {
                CategoryBean cb = cdto.get(i);
              %>
                <label class="inline-flex items-center space-x-2">
                  <input type="checkbox" name="categoryfilter" class="category-filter" value="<%= cb.getId() %>">
                  <!-- 色バッジ -->
                  <span class="inline-block px-3 py-1 rounded-full text-sm font-semibold <%= cb.getCategoryClass() %>">
                    <%= cb.getCategoryName() %>
                  </span>
                </label>
              <% } %>
            </div>
          </div>
        <% } %>


        <% if(tdto.size() == 0) {%>
          <p class="text-gray-600">登録されているタスクはありません。</p>
        <% } %>
        <% if(tdto.size() != 0) {%>
          <table class="min-w-full border border-gray-300 rounded-lg overflow-hidden">
            <thead class="bg-blue-100">
              <tr>
                <th class="py-2 px-4 border-b">内容</th>
                <th class="py-2 px-4 border-b">状態</th>
                <th class="py-2 px-4 border-b">登録日</th>
                <th class="py-2 px-4 border-b text-center">操作</th>
              </tr>
            </thead>
            <tbody>
            <% for(int i = 0; i < tdto.size(); i++) {
              TodoBean tb = tdto.get(i);
            %>
            <form action="/todoApp/todo" method="post" class="mb-6">
              <tr id="row-<%= tb.getNo() %>" class="group hover:bg-gray-50" data-category="<%= tb.getCategoryId() %>">
                <td class="py-2 px-4 border-b"><%= tb.getTask() %></td>
                <td class="py-2 px-4 border-b font-bold text-center">
                  <span class="inline-block px-3 py-1 rounded-full font-bold <%= tb.getCategoryClass() %>"><%= tb.getCategory() %></span>
                </td>
                <td class="py-2 px-4 border-b"><%= tb.getDate() %></td>
                <td class="py-2 px-4 border-b text-center relative">
                  <button type="button"
                          class="delete-btn inline-block text-red-500"
                          data-id="<%= tb.getNo() %>"
                          title="削除">
                    ❌
                  </button>
                </td>
              </tr>
            <% } %>
            </tbody>
          </table>
        <% } %>
      </section>
    </main>
  </body>
  <script src="../todoApp/js/todo.js"></script>
</html>