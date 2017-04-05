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
        Display.setTitle("Program 1");
        Display.create();
    }
    
    private void initGL(){
        glClearColor(1f, 1f, 1f, 0.1f);
        
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
                glColor3f(0.7f, 0.3f, 0.3f);
                //glPointSize(10);
                
//                glBegin(GL_LINES);
//                    glVertex2f(10.0f, 380.0f);
//                    glVertex2f(380.0f, 10.0f);
//                glEnd();
//                glBegin(GL_POINTS);
//                    glVertex2f(59.1f, 299f);
//                glEnd();
                float x1, y1, x2, y2;
                //case 1
//                x1 = 10;
//                y1 = 100;
//                x2 = 100;
//                y2 = 120;
                //case 2
//                x2 = 10;
//                y2 = 100;
//                x1 = 100;
//                y1 = 130;
                //case 3, need to work on
                x2 = 10;
                y2 = 130;
                x1 = 100;
                y1 = 100;
//                //case 4
//                x1 = 10;
//                y1 = 130;
//                x2 = 100;
//                y2 = 100;
                drawLine(x1,y1,x2,y2);
//                drawLine(350, 50, 500, 220);
                
                glColor3f(0.5f, 0.3f, 0.9f);
                glPointSize(3);
                glBegin(GL_POINTS);
                    glVertex2f(x1, y1);
                    glVertex2f(x2, y2);
                    //glVertex2f(500, 220);
                glEnd();
                
                Display.update();
                Display.sync(60);
            }
            catch(Exception e){
                
            }
        }
        Display.destroy();
    }
    public void drawCircle(float centerX, float centerY, float radius){
        
    }
    public void drawLine(float x1, float y1, float x2, float y2){
        //Declare and initialize dx, dy, d, incrementRight, incrementUpRight, x, and y
        float dx, dy, d, incrementRight, incrementUpRight, x, y, slope;
        
        //dx = x2 - x1
        dx = x2 - x1;
        //dy = y2 - y1
        dy = y2 - y1;
        slope = dy / dx;
        
        if(slope > -1 && slope <1 ){
            
            //d = 2(dy)-dx
            d = (float) (dy - 0.5 * dx);
            //incrementRight = 2*dy
            incrementRight = 2 * dy;
            //incrementUpRight = 2*(dy-dx)
            incrementUpRight = 2 * (dy - dx);
            //x = x1
            x = x1;
            //y = y1
            y = y1;
            //draw first point
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            
            
            //when slope is positive
            if(slope >0){
                //if x1 and y1 are on the left of x2 and y2
                if(dx >0 && dy >0){
                    //d = 2(dy)-dx
                    d = (float) (dy - 0.5 * dx);
                    while(x < x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y+1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
//                            d = 2*(dy-dx) + d;
                              d = d + dy - dx;
                        }
                        else{
                            //x+1
                            x = x + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
//                            d = d + 2 * dy;
                              d = d + dy;
                        }
                    }
                }
                //if x1 and y1 are on the right of x2 and y2
                else if(dx < 0 && dy < 0){
                    //d = 0.5dx-dy
                    d = (float) (0.5*dx - dy);
                    while(x > x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x-1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = d - (dy - dx);
                        }
                        else{
                            //x+1
                            x = x - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d - dy;
                        }
                    }
                }
            }
            //for slope is negative
            else if (slope < 0){
                //if x1 and y1 are on the right of x2 and y2
                if(dx < 0 && dy >0){
                    dy = -dy;
                    //d = 2(dy)-dx
                    d = (float) (0.5*dx - dy);
                    while(x > x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x-1;
                            y = y+1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
                            d = d - (dy - dx);
                        }
                        else{
                            //x+1
                            x = x - 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
                            d = d - dy;
                        }
                    }
                    
                }
                //if x1 and y1 are on the left of x2 and y2
                else if (dx > 0 && dy < 0){
                    dy = -dy;
                    d = (float) (dy - 0.5 * dx);
                    while(x < x2){
                        if(d > 0){
                            //x+1, y+1
                            x = x+1;
                            y = y-1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = 2(dy-dx)+d(old)
//                            d = 2*(dy-dx) + d;
                              d = d + dy - dx;
                        }
                        else{
                            //x+1
                            x = x + 1;
                            //draw new (x,y)
                            glBegin(GL_POINTS);
                                glVertex2f(x,y);
                            glEnd();
                            //d = d(old)+2dy
//                            d = d + 2 * dy;
                              d = d + dy;
                        }
                    }
                }
            }
            
            //while(x < x2)

            
        }
        else{
            //d = (0.5 * dy)-dx
            d = (float) ((0.5 * dy) -dx);
            //incrementRight = 2*dy
            incrementRight = 2 * dy;
            //incrementUpRight = 2*(dy-dx)
            incrementUpRight = 2 * (dy - dx);
            //x = x1
            x = x1;
            //y = y1
            y = y1;
            //draw first point
            glBegin(GL_POINTS);
                glVertex2f(x,y);
            glEnd();
            //while(x < x2)

            while(y < y2){
                if(d > 0){
                    //x+1, y+1
                    x = x+1;
                    y = y+1;
                    //draw new (x,y)
                    glBegin(GL_POINTS);
                        glVertex2f(x,y);
                    glEnd();
                    //d = 2(dy-dx)+d(old)
                    d = 2*(dx-dy) + d;
//                    d = d + dx - dy;
                }
                else{
                    //x+1
                    y = y + 1;
                    //draw new (x,y)
                    glBegin(GL_POINTS);
                        glVertex2f(x,y);
                    glEnd();
                    //d = d(old)+2dy
                    d = d + 2 * dx;
//                    d = d + dx;
                }
            }
        }
            
                
                
    }
    public void drwaElipse(float centerX, float centerY, float radius1, float radius2){
        
    }
    
    public static void main(String[] args){
        Program1 p = new Program1();
        p.start();
    }
}