<template>
  <div class="bg">
    <the-navigation class="nav"></the-navigation>
    <div style="height: 90%; padding: 1rem 3rem">
      <h2 style="position: relative; bottom: 5px">Danh sách các đầu sách</h2>
      <table class="table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
            <th>Date of issue</th>
            <th>Pages</th>
            <!-- <th>Action</th> -->
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in posts" :key="post.number">
            <td>{{ post.title }}</td>
            <td>{{ post.author }}</td>
            <td>{{ post.category }}</td>
            <td>{{ post.dateofissue }}</td>
            <td>{{ post.pages }}</td>
            <!-- <td></td> -->
          </tr>
        </tbody>
        <!-- <div>
          <router-link to="/posts">Go to posts page</router-link>
        </div> -->
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TheNavigation from "@/components/TheNavigation.vue";

export default {
  components: { TheNavigation },
  data() {
    return {
      posts: {
        title: "",
        author: "",
        discription: "",
        dateofissue: "",
        pages: "",
        category: "",
      },
    };
  },

  async mounted() {
    let result = await axios.get(`http://localhost:6060/books`);
    this.posts = result.data;
    console.log(this.posts);
  },
};
</script>

<style scoped>
:root {
  scroll-behavior: smooth;
}
.nav {
  position: sticky;
  top: 0;
}
body {
  color: #444;
  font: 100%/30px "Helvetica Neue", helvetica, arial, sans-serif;
  text-shadow: 0 1px 0 #fff;
}

.bg {
  width: 100%;
  min-height: 100%;
  background: linear-gradient(to right, #80878d, #2c3e50);
}

strong {
  font-weight: bold;
}

em {
  font-style: italic;
}

table {
  background: #f5f5f5;
  border-collapse: separate;
  box-shadow: inset 0 1px 0 #fff;
  font-size: 12px;
  line-height: 24px;
  margin: 30px auto;
  text-align: left;
  width: 80%;
  height: 72vh;
}

thead {
  top: 0;
  height: 4.5rem;
  font-size: 1.5rem;
  vertical-align: middle;
}

th {
  background: linear-gradient(#777, #444);
  border-left: 1px solid #555;
  border-right: 1px solid #777;
  border-top: 1px solid #555;
  border-bottom: 1px solid #333;
  box-shadow: inset 0 1px 0 #999;
  color: #fff;
  font-weight: bold;
  padding: 10px 15px;
  position: relative;
  text-shadow: 0 1px 0 #000;
}

th:after {
  background: linear-gradient(
    rgba(255, 255, 255, 0),
    rgba(255, 255, 255, 0.08)
  );
  content: "";
  display: block;
  height: 25%;
  left: 0;
  margin: 1px 0 0 0;
  position: absolute;
  top: 25%;
  width: 100%;
}

th:first-child {
  border-left: 1px solid #777;
  box-shadow: inset 1px 1px 0 #999;
}

th:last-child {
  box-shadow: inset -1px 1px 0 #999;
}

td {
  max-width: 260px;
  border-right: 1px solid #fff;
  border-left: 1px solid #e8e8e8;
  border-top: 1px solid #fff;
  border-bottom: 1px solid #e8e8e8;
  padding: 10px 15px;
  position: relative;
  transition: all 300ms;
  height: 3.5rem;
  vertical-align: middle;
  font-size: 1.1rem;
}

td:first-child {
  box-shadow: inset 1px 0 0 #fff;
}

td:last-child {
  border-right: 1px solid #e8e8e8;
  box-shadow: inset -1px 0 0 #fff;
}

tr:nth-child(odd) td {
  background: #f1f1f1;
}

tr:last-of-type td {
  box-shadow: inset 0 -1px 0 #fff;
}

tr:last-of-type td:first-child {
  box-shadow: inset 1px -1px 0 #fff;
}

tr:last-of-type td:last-child {
  box-shadow: inset -1px -1px 0 #fff;
}

tbody:hover td {
  color: transparent;
  text-shadow: 0 0 3px #aaa;
}

tbody:hover tr:hover td {
  transition: 0.1s ease-in-out;
  font-weight: bolder;
  color: black;
  text-shadow: 0 1px 0 #fff;
}

::-webkit-scrollbar {
  display: none;
}

/* ::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
} */

@media (max-width: 768px) {
  ::-webkit-scrollbar {
    display: none;
  }
  .table {
    display: block;
    width: 100%;
  }

  .table thead,
  .table tbody,
  .table thead th {
    display: block;
  }

  .table thead {
    height: 100%;
    float: left;
  }

  .table tbody {
    height: 100%;
    width: auto;
    position: relative;
    overflow-x: auto;
  }

  .table td,
  .table th {
    height: 70px;
    vertical-align: middle;
    box-sizing: border-box;
    overflow-x: hidden;
    overflow-y: auto;
    width: 120px;
    font-size: 13px;
    text-overflow: ellipsis;
  }
  .table thead tr {
    height: 100%;
  }

  .table thead th {
    height: 12vh;
    text-align: left;
  }

  .table tbody tr {
    height: 100%;
    display: table-cell;
  }

  .table tbody td {
    height: 12vh;
    display: block;
    text-align: center;
  }
}
</style>
