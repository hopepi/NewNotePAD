package com.example.newnote.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.newnote.viewModel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newnote.model.NewNoteModel
import com.example.newnote.model.addDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {

    val notes by viewModel.notes.collectAsState()

    /*
        Destructuring Declarations
        Bu özellik, bir fonksiyon veya ifade tarafından döndürülen çoklu değerleri bir kerede ayırmanıza
        ve değişkenlere atamanıza olanak tanır
        dialogOpen, MutableState<Boolean> nesnesini temsil eder
        setDialogOpen, bu nesnenin value özelliğini değiştirir
     */
    val (dialogOpen,setDialogOpen) = remember{
        mutableStateOf(false)
    }

    val(title,setTitle) = remember {
        mutableStateOf("")
    }
    val(subTitle,setSubTitle) = remember {
        mutableStateOf("")
    }

    if (dialogOpen){
        Dialog(onDismissRequest = {setDialogOpen(false)}) {
            Column (
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ){
                OutlinedTextField(
                    value = title,
                    onValueChange ={
                        setTitle(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Title")
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedLabelColor = Color.White,
                        unfocusedTextColor = Color.White,
                        disabledTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = subTitle,
                    onValueChange ={
                        setSubTitle(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Title")
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        focusedLabelColor = Color.White,
                        unfocusedTextColor = Color.White,
                        disabledTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(onClick = {
                    if (title.isNotEmpty() && subTitle.isNotEmpty()){
                        viewModel.addNote(NewNoteModel(
                            title = title,
                            subTitle = subTitle
                        ))
                        setDialogOpen(false)
                    }
                }, shape = RoundedCornerShape(5.dp), modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )) {
                    Text(text = "Submit", color = Color.White)
                }
            }
        }
    }

    Scaffold (
        containerColor = MaterialTheme.colorScheme.secondary,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    setDialogOpen(true)
                },
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary


            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
    {paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), contentAlignment = Alignment.Center)
        {
            /*
            AnimatedVisibility bileşeni Jetpack Compose'da bir
            bileşenin görünürlüğünü animasyonlu bir şekilde kontrol etmenizi sağlar

            enter: Bileşen görünür hale gelirken uygulanacak giriş animasyonunu belirler. scaleIn() ve fadeIn()
            animasyonlarının birleşimi kullanılmıştır.

            exit: Bileşen gizlenirken uygulanacak çıkış animasyonunu belirler. scaleOut() ve fadeOut()
            animasyonlarının birleşimi kullanılmıştır.
             */
            AnimatedVisibility(visible = notes.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(text = "You have not note", color = Color.White, fontSize = 22.sp)
            }
            AnimatedVisibility(visible = notes.isNotEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                LazyColumn (modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding() + 8.dp,
                        top = 8.dp,
                        end = 8.dp,
                        start = 8.dp
                    ), verticalArrangement = Arrangement.spacedBy(8.dp)


                ){
                    /*
                    1. notes.sortedBy { it.done }:
                    Bu ifade, notes listesini done özelliğine göre sıralar.
                    sortedBy fonksiyonu, verilen bir lambda ifadesine göre listeyi sıralar.
                    it.done kısmı, her bir note öğesinin done özelliğini ifade eder.
                    Bu sıralama, done değeri false olan notları listenin başına, true olan notları ise listenin sonuna yerleştirir.

                    Örneğin:
                    notes listesi şu şekilde olsun: [Note1(done = true), Note2(done = false), Note3(done = true)]
                    sortedBy { it.done } ifadesi, listeyi şu şekilde sıralar: [Note2(done = false), Note1(done = true), Note3(done = true)]

                    --------------------------------------------------------------------------------------------------------------------------------------------------------
                    2. key = {it.id}:
                    key parametresi, her bir liste öğesi için benzersiz bir anahtar sağlar.
                    Bu anahtar, Compose'un her bir öğeyi benzersiz olarak tanımlamasına ve gerektiğinde yeniden çizim yaparken doğru öğeyi bulmasına yardımcı olur.
                    {it.id} ifadesi, her bir note öğesinin id özelliğini anahtar olarak kullanır.
                    Bu, özellikle listede ekleme, silme veya güncelleme işlemleri sırasında performansı artırır ve doğru öğelerin yeniden çizilmesini sağlar.

                    1. Dinamik Listeler:
                    Öğe Eklenmesi: Listenize yeni öğeler eklediğinizde, key kullanmak Compose'un doğru öğeyi tanımlamasına yardımcı olur.
                    Öğe Silinmesi: Listenizden öğeler çıkardığınızda, key kullanmak kalan öğelerin doğru şekilde yeniden düzenlenmesini sağlar.
                    Öğe Güncellenmesi: Listenizdeki mevcut öğeleri güncellediğinizde, key kullanmak doğru öğenin güncellenmesini sağlar.

                    2. Performans Optimizasyonu:
                    Gereksiz Yeniden Çizimleri Önlemek: key kullanarak, Compose'un yalnızca gerçekten değişen öğeleri yeniden çizmesini sağlayabilirsiniz.
                    Bu, performansı artırır ve gereksiz yeniden çizimleri önler.
                    Doğru Yeniden Kullanım: key sayesinde, liste öğelerinin doğru şekilde yeniden kullanılması sağlanır. Bu, özellikle büyük listelerle çalışırken önemlidir.


                    Örnek Senaryolar:
                    1. Sabit Listeler:
                    Eğer listeniz sabitse ve öğeler hiçbir zaman eklenmeyecek, silinmeyecek veya güncellenmeyecekse,
                    key kullanmanız gerekmeyebilir. Ancak, bu durumlar nadirdir.

                    2. Dinamik Listeler
                    Bu örnekte, notes listesi dinamik olarak değişebilir. key = { it.id } ifadesi,
                    Compose'un her NoteItem bileşenini benzersiz bir şekilde tanımlamasına ve yönetmesine olanak tanır.


                    Özet
                    Key ifadesini şu durumlarda kullanmanız doğru olacaktır:
                    Dinamik listeler: Öğeler eklenip siliniyor veya güncelleniyorsa.
                    Performans optimizasyonu: Gereksiz yeniden çizimleri önlemek ve doğru yeniden kullanımı sağlamak için.
                    Benzersiz öğe tanımlaması: Liste öğelerini benzersiz bir şekilde tanımlamak ve yönetmek için.
                     */
                    items(notes.sortedBy { it.done }, key = {it.id}){ note ->
                        NewNoteItem(noteModel = note,
                            onClick = {
                                viewModel.updateNote(note.copy(done = !note.done))
                        }, onDelete = {
                            viewModel.deleteNote(note)
                        })

                    }

                }
            }


        }

    }


}

//sor
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.NewNoteItem(noteModel: NewNoteModel,onClick:()->Unit,onDelete:()->Unit) {

    val color by animateColorAsState(targetValue = if (noteModel.done){Color(0xff24d65f)} else{Color(0xffff6363)},
        animationSpec = tween(500)
    )

    Box (modifier = Modifier.fillMaxWidth().animateItemPlacement(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    ), contentAlignment = Alignment.BottomEnd){

        Row (modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color)
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Box (modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(4.dp),
                    contentAlignment = Alignment.Center
                ){
                    Row {
                        AnimatedVisibility(visible = noteModel.done,
                            enter = scaleIn()+ fadeIn(),
                            exit = scaleOut()+ fadeOut()
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check", tint = color)
                        }
                    }
                    Row {
                        AnimatedVisibility(visible = !noteModel.done,
                            enter = scaleIn()+ fadeIn(),
                            exit = scaleOut()+ fadeOut()
                        ) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Check", tint = color)
                        }
                    }
                }
                Column {
                    Text(text = noteModel.title , fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color= Color.White)
                    Text(text = noteModel.subTitle , fontWeight = FontWeight.Bold, fontSize = 10.sp, color= Color(0xffebebeb))

                }

            }
            Box(modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp),
                contentAlignment = Alignment.Center){
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        }
        Text(text = noteModel.addDate,Modifier.padding(4.dp), color = Color(0xffebebeb), fontSize = 8.sp)

    }
}