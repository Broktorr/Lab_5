package com.example.jetcompous

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import androidx.compose.material.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class User(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val description: String
)

class UserViewModel: ViewModel() {
    val users = MutableStateFlow<List<User>>(listOf(
        User(1, "John Doe", "https://i.etsystatic.com/39530894/r/il/a067d2/4562558346/il_fullxfull.4562558346_g7qx.jpg", "John is a software engineer with a passion for coding."),
        User(2, "Jane Smith", "https://cs12.pikabu.ru/post_img/big/2022/04/09/9/1649513670117830723.png", "Jane is an artist who loves expressing her creativity through various mediums."),
        User(3, "David Johnson", "https://i.pinimg.com/originals/3d/4f/c5/3d4fc5f11456a65949962360fddba5f8.jpg", "David is a photographer who captures breathtaking moments through his lens."),
        User(4, "Emily Brown", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/e8d9c01b-ae96-44c2-82c4-ae1230263a48/ddaw7x4-a44e1f07-36e8-492a-accb-c812dc0b0b32.jpg/v1/fit/w_414,h_316,q_70,strp/aside_justice_there_is_evil_by_skunkyfly_ddaw7x4-414w.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NzgxIiwicGF0aCI6IlwvZlwvZThkOWMwMWItYWU5Ni00NGMyLTgyYzQtYWUxMjMwMjYzYTQ4XC9kZGF3N3g0LWE0NGUxZjA3LTM2ZTgtNDkyYS1hY2NiLWM4MTJkYzBiMGIzMi5qcGciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.4gRbU9BgL3B5u969_GYoEAQd7nEib2okrPaRMairciA", "Emily is a fashion designer who creates unique and stylish clothing."),
        User(5, "Michael Johnson", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3cS1QBNlJe7tZnI69xtX7oxqxakReidrwHQ&usqp=CAU", "Michael is a musician who plays multiple instruments and enjoys composing his own music."),
        User(6, "Sarah Davis", "https://w0.peakpx.com/wallpaper/683/656/HD-wallpaper-kawaii-anime-boy-for-android-anime-boy-love-anime-boy.jpg", "Sarah is a book lover and an avid reader who enjoys exploring different genres."),
        User(7, "Thomas Wilson", "https://pbs.twimg.com/media/FQSuxplXsAITFcN?format=jpg&name=medium", "Thomas is a fitness enthusiast who believes in leading a healthy and active lifestyle."),
        User(8, "Olivia Martinez", "https://i.pinimg.com/originals/1b/90/bb/1b90bb4cf8b58fcc23e96f6a228f5ea3.png", "Olivia is a nature lover and enjoys spending time outdoors, exploring new hiking trails."),
        User(9, "James Taylor", "https://c4.wallpaperflare.com/wallpaper/701/379/747/anime-girls-anime-original-characters-cat-ears-pink-hair-hd-wallpaper-preview.jpg", "James is a chef who specializes in creating exquisite culinary delights."),
        User(10, "Emma Anderson", "https://www.pngkey.com/png/detail/180-1804708_beautiful-anime-girl-manga-anime-anime-art-zero.png", "Emma is a travel blogger who documents her adventures and shares her experiences with her audience."),
        User(11, "William Lee", "https://lunarmimi.net/wp-content/uploads/2022/11/Creative-NAI-Prompts-14.webp", "William is a scientist who conducts research in the field of renewable energy."),
        User(12, "Sophia Clark", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgBe-dBpjmQZrMjlAqya3bWutL7CxeKq8km_PwRJgv7snKqjBDxZ9PTDnCnvV5Wuv4qvXFT2yIoXz-Rljdp5DeHVjUfdwgpTFxugzfLe_oACFDA-jPTZZpjhwLwVrNd-nMvaYTaS3N1scZiU1zAQ1K0YCJ96-xjkItGNBU6Gg6dT4hkonXt6Gn80ryXRg/w1600/anime-cyberpunk-female-soldier-anime-art-ponytail-thumb.webp", "Sophia is an entrepreneur who owns a successful online business."),
        User(13, "Daniel Rodriguez", "https://static.displate.com/280x392/displate/2023-01-19/16e24cb80be2de1ab2fedbf992fba9c4_ad1da4132e8b30cf4fdb5517fb34fd8c.jpg", "Daniel is a graphic designer who brings creativity and innovation to his designs."),
        User(14, "Ava Scott", "https://animesher.com/orig/0/85/855/8550/animesher.com_magic-girl-anime-girl-anime-art-855069.png", "Ava is a teacher who is passionate about inspiring and educating young minds."),
        User(15, "Josephine Young", "https://a-static.besthdwallpaper.com/chainsaw-man-reze-wallpaper-2560x1440-79296_51.jpg", "Josephine is a social worker who is dedicated to making a positive impact in her community."),
        User(16, "Henry Turner", "https://w0.peakpx.com/wallpaper/626/259/HD-wallpaper-anime-girl-art-beautiful-hot-kiz-ninja-pink-sexy.jpg", "Henry is a sports enthusiast and enjoys playing basketball and soccer."),
        User(17, "Chloe Hill", "https://i.pinimg.com/originals/80/d3/ef/80d3efa0bb101ceccbc9191378b33313.jpg", "Chloe is an animal lover and volunteers at a local shelter to help animals in need."),
        User(18, "Benjamin Baker", "https://p4.wallpaperbetter.com/wallpaper/115/284/991/anime-girls-anime-kyrie-meii-wallpaper-preview.jpg", "Benjamin is a journalist who investigates and reports on current events and important issues."),
        User(19, "Mia Phillips", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZV4-47gNPFbeasQYvUV5Q5w-rKEI6roGVjpw4JATb-zSgGdSKn4509mw-80i96FUStjk&usqp=CAU", "Mia is an architect who designs innovative and sustainable buildings."),
        User(20, "Samuel Morris", "https://images4.alphacoders.com/119/1193618.jpg", "Samuel is a firefighter who bravely serves his community and helps keep people safe."),
        User(21, "Natalie Mitchell", "https://foni.club/uploads/posts/2023-03/1677665937_foni-club-p-skelet-anime-art-15.jpg", "Natalie is a veterinarian who cares for animals and ensures their well-being."),
        User(22, "Andrew Nelson", "https://animemotivation.com/wp-content/uploads/2018/06/rock-and-revy-rebecca.jpg", "Andrew is a financial advisor who helps individuals and businesses plan for their financial future."),
        User(23, "Grace Rivera", "https://t3.ftcdn.net/jpg/04/49/20/06/360_F_449200631_gq7SnaFI60z6RetiiyMwHzcwKKIiQoHu.jpg", "Grace is an artist who expresses her emotions and ideas through her beautiful paintings."),
        User(24, "Jack Edwards", "https://i.pinimg.com/236x/25/db/4e/25db4ea11bd30c16236e2d7b18ecb308.jpg", "Jack is a software developer who enjoys creating innovative applications."),
        User(25, "Lily Stewart", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxMeM5UgfZXrVucbAc3EJplwIhWA09vCxnuA&usqp=CAU", "Lily is a fashion blogger who shares her style tips and fashion inspiration with her followers."),
        User(26, "Christopher Collins", "https://cdn.wallpapersafari.com/10/95/WEzrTl.jpg", "Christopher is a filmmaker who tells captivating stories through his movies."),
        User(27, "Ella Murphy", "https://foni.club/uploads/posts/2023-02/1677256401_foni-club-p-devushka-anime-art-na-avu-7.jpg", "Ella is a dancer who is passionate about expressing herself through the art of dance."),
        User(28, "Daniel Cooper", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNNrWYnl5rpmar9gGDvXiMZZ77kZptIQG_pdEkCVSCAfU4e1q62ommBTUs-FMlWjvXG8E&usqp=CAU", "Daniel is a teacher who is dedicated to providing quality education to his students."),
        User(29, "Victoria Reed", "https://i.pinimg.com/736x/75/dc/73/75dc73306e03f7b8ad6b29c40a43ab59.jpg", "Victoria is an environmentalist who works to protect and conserve the natural environment."),
        User(30, "Maxwell Ross", "https://wallpapers.com/images/featured/anime-art-zgganwozkxu1fx9d.jpg", "Maxwell is a pilot who enjoys soaring through the skies and exploring new destinations.")

    ))
    val selectedUser = MutableStateFlow<User?>(null)
    fun onUserSelected(user: User) {
        selectedUser.value = user
    }
}

@Composable
fun UserRow(user: User, onUserClick: (User) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onUserClick(user) }
    ) {
        Image(
            painter = rememberImagePainter(data = user.photoUrl),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 8.dp)
        )
        Text(user.name, style = MaterialTheme.typography.h5, color = Color.White)
    }
}

@Composable
fun MyApp() {
    val viewModel: UserViewModel = viewModel()
    val navController = rememberNavController()
    val users by viewModel.users.collectAsState()

    NavHost(navController, startDestination = "user_list") {
        composable("user_list") {
            UserListScreen(users) { user ->
                viewModel.onUserSelected(user)
                navController.navigate("user_profile")
            }
        }

        composable("user_profile") {
            val selectedUser by viewModel.selectedUser.collectAsState()
            selectedUser?.let {
                UserProfileScreen(it) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Composable
fun UserListScreen(users: List<User>, onUserClick: (User) -> Unit) {
    LazyColumn {
        items(users) { user ->
            UserRow(user, onUserClick)
        }
    }
}

@Composable
fun UserProfileScreen(user: User, onBackClick: () -> Unit) {
    val isExpanded = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberImagePainter(data = user.photoUrl),
            contentDescription = null,
            modifier = Modifier
                .height(if (isExpanded.value) 800.dp else 400.dp)
                .fillMaxWidth()
                .clickable { isExpanded.value = !isExpanded.value }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = "Name: " + user.name, style = MaterialTheme.typography.h5, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "About me: " + user.description, style = MaterialTheme.typography.body1, color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onBackClick) {
                Text("Go back")
            }
        }
    }
}


