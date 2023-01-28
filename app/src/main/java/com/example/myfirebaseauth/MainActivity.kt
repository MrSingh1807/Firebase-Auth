package com.example.myfirebaseauth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.myfirebaseauth.viewModel.MainViewModel
import com.example.myfirebaseauth.viewModel.MainViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var mainViewModel: MainViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        auth = FirebaseAuth.getInstance()
//        authStateListener


// *********  Edit Texts  ***********  //
        et_EmailId.setOnClickListener {
            tv_emailShow.isVisible
        }

        et_password.setOnClickListener{
            tv_passwordShow.isVisible
        }

        et_userName.setOnClickListener {
            tv_userNameShow.isVisible
        }

// *********  Buttons  ***********  //
        btn_register.setOnClickListener {
            createUser()
        }

        btn_signIn.setOnClickListener {
            signInUser()
        }
        btn_signOut.setOnClickListener {

        }
    }

    private fun signInUser(){
        val userEmail = et_EmailId.text.toString().trim()
        val passWord = et_password.text.toString().trim()
    //   code for Sign User
        if (validateEmail(userEmail) && validatePassword(passWord)) {
            showProgressBar(true)
            auth.signInWithEmailAndPassword(userEmail,passWord)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Signed In", Toast.LENGTH_SHORT).show()
                    showProgressBar(false)

                    tv_result.text = it.user?.email
                }
        }
    }

    private fun createUser(){
        val userEmail = et_EmailId.text.toString().trim()
        val passWord = et_password.text.toString().trim()
        if (validateEmail(userEmail) && validatePassword(passWord)){
            showProgressBar(true)
            auth.createUserWithEmailAndPassword(userEmail,passWord)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                    showProgressBar(false)
                }
        }
    }


    private fun validateEmail(email: String): Boolean {

        if (email.isEmpty()){
            Toast.makeText(this, "Please Enter an Email ID",Toast.LENGTH_SHORT).show()
            val text = "Email ID is Required"
            emailError(text)

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            val text = "Please Provide a valid Email ID"
            Toast.makeText(this, text,Toast.LENGTH_SHORT).show()
            emailError(text)
        } else{
            return true
        }
        return false
    }

    private fun validatePassword(passWord: String): Boolean{

        if (passWord.isEmpty()){
            Toast.makeText(this, "Please Enter password",Toast.LENGTH_SHORT).show()
            val text = "Password is Required"
            passWordError(text)
        } else if ( passWord.length < 6){
            val text = "Password does't less than 6"
            Toast.makeText(this, text,Toast.LENGTH_SHORT).show()
            passWordError(text)
        } else {
            return true
        }
        return false
    }

    private fun showProgressBar(show: Boolean){
        if (show){
            progressBar.isVisible
        }
    }

    @SuppressLint("ResourceAsColor")
    fun emailError(text: String){
        tv_emailError.isVisible
        tv_emailError.text = text
        tv_emailError.setBackgroundColor(R.color.red)
        et_EmailId.setBackgroundColor(R.color.red)
    }
    @SuppressLint("ResourceAsColor")
    fun passWordError(text: String){
        tv_passwordError.isVisible
        tv_passwordError.text = text
        tv_passwordError.setBackgroundColor(R.color.red )
        et_password.setBackgroundColor(R.color.red)


    }

}
