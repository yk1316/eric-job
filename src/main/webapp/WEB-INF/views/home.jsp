<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
 <head>
   <title>Coding Java Spring Demo</title>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   
   <style type="text/css">
     body {
       font-family: "Microsoft YaHei",微软雅黑,"Microsoft JhengHei","Hiragino Sans GB",Verdana, Geneva, sans-serif;
     }

     .wrapper {
       width: 900px;
       margin: 0 auto;
     }

     p {
       margin-top: 20px;
     }

     a:link {
       text-decoration: none;
       color: #2d59a2;
     }

     a:visited {
       text-decoration: none;
       color: #2d59a2;
     }

     a:hover {
       text-decoration: none;
       color: #203f71;
     }

     a:active {
       text-decoration: none;
       color: #203f71;
     }

     #forkongithub a {
       background: #000;
       color: #fff;
       text-decoration: none;
       font-family: arial,sans-serif;
       text-align: center;
       font-weight: bold;
       padding: 5px 40px;
       font-size: 1rem;
       line-height: 2rem;
       position: relative;
       transition: 0.5s;
     }

     #forkongithub a:hover {
       background: #c11;
       color: #fff;
     }

     #forkongithub a::before,#forkongithub a::after {
       content: "";
       width: 100%;
       display: block;
       position: absolute;
       top: 1px;
       left: 0;
       height: 1px;
       background: #fff;
     }

     #forkongithub a::after {
       bottom: 1px;
       top: auto;
     }

     @media screen and (min-width:800px) {
       #forkongithub {
         position: absolute;
         display: block;
         top: 0;
         right: 0;
         width: 200px;
         overflow: hidden;
         height: 200px;
         z-index: 9999;
       }

       #forkongithub a {
         width: 200px;
         position: absolute;
         top: 60px;
         right: -60px;
         transform: rotate(45deg);
         -webkit-transform: rotate(45deg);
         -ms-transform: rotate(45deg);
         -moz-transform: rotate(45deg);
         -o-transform: rotate(45deg);
         box-shadow: 4px 4px 10px rgba(0,0,0,0.8);
       }
     }
   </style>
 </head>
 <body>
  <div class="wrapper">
   <p>这是 <a href="https://coding.net" target="_blank">Coding</a> 演示平台 <a href="https://coding.net/u/demo/p/spring-hello/git" target="_blank">Java Spring</a> 示例项目运行效果页面。</p>
   <p>
     本页面是一个静态页面，动态页面示例：<a href="/env" style="color: green; text-decoration: underline;">输出系统环境变量</a>
   </p>

   <span id="forkongithub"><a href="https://coding.net/u/demo/p/spring-hello/git">Fork me on Coding</a></span>
   <iframe id="iframepage" name="iframepage" frameborder="0" scrolling="no" width="100%" height="1300px" class="iframe.demo" src="https://coding.net/demo.html" />
  </div>
 </body>
</html>
