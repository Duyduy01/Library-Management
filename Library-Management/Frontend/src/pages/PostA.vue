<template>
  <div class="bg">
    <the-navigation class="nav"></the-navigation>
    <div class="container">
      <h1 style="position: relative; top: 5px">Hello {{ displayName }}</h1>
      <h2 style="position: relative; top: 5px">Danh sách các đầu sách</h2>
      <table class="table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
            <th>Date of issue</th>
            <th>Pages</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in posts" :key="post.number">
            <td>{{ post.title }}</td>
            <td>{{ post.author }}</td>
            <td>{{ post.category }}</td>
            <td>{{ post.dateofissue }}</td>
            <td>{{ post.pages }}</td>
            <td>
              <div class="showBtn">
                <div class="btnV">
                  <button
                    class="viewBook"
                    @click="$router.push('/view-book/' + post.id)"
                  >
                    View
                  </button>
                </div>
                <div class="btnD">
                  <button class="noselect" v-on:click="deleteBook(post.id)">
                    <span class="text">Delete</span>
                    <span class="icon">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="24"
                        height="24"
                        viewBox="0 0 24 24"
                      >
                        <path
                          d="M24 20.188l-8.315-8.209 8.2-8.282-3.697-3.697-8.212 8.318-8.31-8.203-3.666 3.666 8.321 8.24-8.206 8.313 3.666 3.666 8.237-8.318 8.285 8.203z"
                        ></path>
                      </svg>
                    </span>
                  </button>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axiosInstance from "../services/AxiosTokenInstance";
import { mapGetters, mapMutations } from "vuex";
import { GET_USER_TOKEN_GETTER } from "../store/storeconstants";
import { LOADING_SPINNER_SHOW_MUTATION } from "../store/storeconstants";
import axios from "axios";
import TheNavigation from "@/components/TheNavigation.vue";
import { storage } from "../firebase";
import { ref, deleteObject } from "firebase/storage";

export default {
  components: { TheNavigation },
  data() {
    return {
      displayName: "",
      posts: [],
      xmlns: "http://www.w3.org/2000/svg",
    };
  },
  computed: {
    ...mapGetters("auth", {
      token: GET_USER_TOKEN_GETTER,
    }),
  },
  mounted() {
    this.showLoading(true);
    axiosInstance
      .get(`http://localhost:6060/books`)
      .then((response) => {
        this.formatPosts(response.data);
        this.showLoading(false);
      })
      .catch(() => {
        this.showLoading(false);
      });
    let userData = localStorage.getItem("userData");
    this.displayName = JSON.parse(userData).displayName;
    // this.loadData();
  },
  methods: {
    ...mapMutations({
      showLoading: LOADING_SPINNER_SHOW_MUTATION,
    }),
    formatPosts(posts) {
      for (let key in posts) {
        this.posts.push({ ...posts[key], number: key });
      }
      console.log(this.posts);
    },
    async deleteBook(id) {
      const storageRef = ref(storage, `images/img${id}`);
      deleteObject(storageRef)
        .then(() => {
          console.log("delete success");
        })
        .catch((error) => {
          console.log(error);
        });
      // const snapshot = await deleteObject(storageRef, this.$refs.myfile.files[0]);
      // console.log(snapshot);
      let delete1 = await axios.delete(
        "http://localhost:6060/book/delete/" + id
      );
      if (delete1.status == 200) {
        console.log(delete1);
        this.loadData();
      }
    },
    async loadData() {
      let result = await axios.get(`http://localhost:6060/books`);
      this.posts = result.data;
    },
  },
};
</script>

<style scoped>
:root {
  scroll-behavior: smooth;
}
.bg {
  width: 100%;
  min-height: 100%;
  background: linear-gradient(to right, #80878d, #2c3e50);
}
.nav {
  position: sticky;
  top: 0;
}
.container {
  height: 90%;
  padding: 0 3rem;
}

body {
  color: #444;
  font: 100%/30px "Helvetica Neue", helvetica, arial, sans-serif;
  text-shadow: 0 1px 0 #fff;
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
  width: 90%;
  height: 72vh;
}

thead {
  height: 4.5rem;
  font-size: 1.5rem;
  vertical-align: middle;
  text-align: center;
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
  max-width: 230px;
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
  max-width: 270px;
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
  font-weight: bolder;
  transition: 0.1s ease-in-out;
  color: black;
  text-shadow: 0 1px 0 #fff;
}

.showBtn {
  margin: 20px 0px;
  height: 100%;
  width: 100%;
  text-align: center;
}
.btnV,
.btnD {
  height: 50%;
  width: 50%;
}
.showBtn .btnV,
.showBtn .btnD {
  display: flex;
  justify-content: center;
  align-items: center;
  float: left;
  width: 50%;
  height: 100%;
}
.viewBook {
  height: 50px;
  width: 100px;
  padding: 15px 25px;
  border: unset;
  border-radius: 15px;
  color: #212121;
  z-index: 1;
  background: #bebebe;
  position: relative;
  font-weight: 1000;
  font-size: 15px;
  -webkit-box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  transition: all 250ms;
  overflow: hidden;
  /* margin: 0 5% 0 5%; */
}

.viewBook::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 0;
  border-radius: 15px;
  background-color: #33cc00;
  z-index: -1;
  -webkit-box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
  transition: all 250ms;
}

.viewBook:hover {
  color: #d7d7d7;
}

.viewBook:hover::before {
  width: 100%;
}

.viewBook a {
  text-decoration: none;
  color: #000;
}

.noselect {
  width: 100px;
  height: 50px;
  cursor: pointer;
  display: flex;
  align-items: center;
  background: red;
  border: none;
  border-radius: 5px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.15);
  background: #e62222;
  /* margin: 0 5% 0 5%; */
}

.noselect,
.noselect span {
  transition: 200ms;
}

.noselect .text {
  transform: translateX(6%);
  color: white;
  font-weight: bold;
}

.noselect .icon {
  position: absolute;
  border-left: 1px solid #c41b1b;
  transform: translateX(60px);
  height: 40px;
  width: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.noselect svg {
  width: 15px;
  fill: #eee;
}

.noselect:hover {
  background: #ff3636;
}

.noselect:hover .text {
  color: transparent;
}

.noselect:hover .icon {
  width: 90px;
  border-left: none;
  transform: translateX(0);
}

.noselect:focus {
  outline: none;
}

.noselect:active .icon svg {
  transform: scale(0.8);
}

@media (max-width: 1200px) {
  .showBtn {
    display: grid;
    gap: 1rem;
  }
  .showBtn .btnV,
  .showBtn .btnD {
    width: 100%;
  }

  .btnV {
    margin-top: 10px;
  }

  .btnD {
    margin-bottom: 10px;
  }
  .viewBook {
    box-shadow: 4px 3px 19px -3px rgba(0, 0, 0, 0.27);
  }
}
@media (max-width: 768px) {
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
  .showBtn {
    gap: 0.5rem;
  }
  .btnD,
  .btnV {
    margin: 0;
  }
  .viewBook {
    width: 75px;
    height: 30px;
    padding: 0;
    font-size: 13px;
  }
  .noselect {
    width: 75px;
    height: 30px;
  }
  .noselect .text {
    transform: none;
  }

  .noselect .icon {
    height: 30px;
    width: 25px;
    transform: translate(44px);
  }
  .noselect svg {
    width: 10px;
  }

  .noselect:hover .icon {
    width: 65px;
  }
}
</style>
