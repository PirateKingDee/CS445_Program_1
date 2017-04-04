/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package program.pkg1;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Program1{
    
    public void start(){
        try{
            createWindow();
            initGL();
            render();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void createWindow()throws Exception{
        Display.setFullscreen(false);
        
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Hey Mom! I am using OpenGL!!");
        Display.create();
    }
    
    private void initGL(){
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(0, 640, 0, 480, 1, -1);
        
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render(){
        while(!Display.isCloseRequested()){
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glColor3f(1.0f, 1.0f, 0.0f);
                glPointSize(10);
                
                glBegin(GL_POINTS);
                    glVertex2f(350.0f, 105.0f);
                    glVertex2f(50.0f, 50.0f);
                glEnd();
                
                Display.update();
                Display.sync(60);
            }
            catch(Exception e){
                
            }
        }
        Display.destroy();
    }
    public static void main(String[] args){
        Program1 p = new Program1();
        p.start();
    }
}