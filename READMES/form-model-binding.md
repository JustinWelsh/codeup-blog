#Spring Fundamentals
*Form Model Binding*

##Notes:


#Exercise:
1. Refactor your [PostController](/Users/justinwelsh/IdeaProjects/springblog/src/main/java/com/codeup/springblog/controllers/PostController.java) and [create](/Users/justinwelsh/IdeaProjects/springblog/src/main/resources/templates/posts/create.html) form to implement form model binding.

```java
//PostController

    @GetMapping("/posts/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post post) {
        // save the ad...
        postDao.save(post);
        // redirect to to the index with all the ads
        return "redirect:/posts";

    }
```
```html
<!--create.html-->

<form th:action="@{/posts/create}" th:method="post" th:object="${post}">
    <label for="title">Title:</label>
    <input th:field="*{title}" id="title" />
    <br />
    <label for="body">Body:</label>
    <input th:field="*{body}" id="body" />
    <br />
    <input type="submit" />
</form>
```

2. Create a controller method and HTML template for viewing a form to edit a specific post.
(This method should map to /posts/{id}/edit.)


```java
//PostController

    @GetMapping("/posts/{id}/edit")
    public String editPosts(Model model, @PathVariable long id) {
        model.addAttribute("post", postDao.getById(id));
        return "posts/create";
    }
```

3. When you view this page, the form should be pre-populated with the values from an existing post.

###*BONUS*

You'll notice that the forms for creating and editing a post are almost entirely the same. How could you refactor your code to avoid duplication?

```html
<!--create.html-->

<!--<form th:action="@{/posts/create}" th:method="post" th:object="${post}">-->
<!--    <label for="title">Title:</label>-->
<!--    <input th:field="*{title}" id="title" />-->
<!--    <br />-->
<!--    <label for="body">Body:</label>-->
<!--    <input th:field="*{body}" id="body" />-->
<!--    <br />-->

    <input type="hidden" th:field="*{id}">

<!--    <input type="submit" />-->
<!--</form>-->
```

##Walkthrough Notes:

