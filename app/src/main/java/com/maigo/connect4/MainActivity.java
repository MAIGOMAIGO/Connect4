package com.maigo.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private final List<Integer> buttonList = new ArrayList<>();
    private final int[][] grid = new int[6][7];

    private ImageView viewPlayer1;
    private ImageView viewPlayer2;

    private int player = 1;
    private int turn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPlayer1 = findViewById(R.id.imageView1);
        viewPlayer2 = findViewById(R.id.imageView2);

        Glide.with(this).asGif().load(R.drawable.player1).into(viewPlayer1);
        Glide.with(this).asGif().load(R.drawable.player2).into(viewPlayer2);

        //initialize array
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid[0].length; col++){
                grid[row][col] = 0;
                buttonList.add(grid[row][col]);
            }
        }

        // GridViewのインスタンスを生成
        GridView gridview = findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を
        // activity_main.xml に inflate するためにGridAdapterに引数として渡す
        GridAdapter adapter = new GridAdapter(
                this.getApplicationContext(),
                R.layout.grid_items,
                buttonList
        );

        // gridViewにadapterをセット
        gridview.setAdapter(adapter);

        // item clickのListenerをセット
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int play = position%7;
        boolean validPlay = validate(play,grid);
        if(validPlay && player != 0){
            //drop the checker
            for (int row = grid.length-1; row >= 0; row--){
                if(grid[row][play] == 0){
                    grid[row][play] = player;
                    // gridView review
                    int targetPosition = row*7+play;
                    buttonList.set(targetPosition,player);
                    View targetView = parent.getChildAt(targetPosition);
                    parent.getAdapter().getView(targetPosition,targetView,parent);
                    break;
                }
            }

            //determine if there is a winner
            boolean winner = isWinner(player,grid);

            //switch players
            if (player == 1){
                player = 2;
            }else{
                player = 1;
            }

            turn++;

            if(isFinish(player,turn,winner)){
                player = 0;
            }
        }else if(player == 0){
            resetGame();
        }
    }

    public static boolean validate(int column, int[][] grid){
        //valid column?
        if (column < 0 || column > grid[0].length){
            return false;
        }

        //full column?
        if (grid[0][column] != 0){
            return false;
        }

        return true;
    }

    public static boolean isWinner(int player, int[][] grid){
        //check for 4 across
        for(int row = 0; row<grid.length; row++){
            for (int col = 0;col < grid[0].length - 3;col++){
                if (grid[row][col] == player   &&
                        grid[row][col+1] == player &&
                        grid[row][col+2] == player &&
                        grid[row][col+3] == player){
                    return true;
                }
            }
        }
        //check for 4 up and down
        for(int row = 0; row < grid.length - 3; row++){
            for(int col = 0; col < grid[0].length; col++){
                if (grid[row][col] == player   &&
                        grid[row+1][col] == player &&
                        grid[row+2][col] == player &&
                        grid[row+3][col] == player){
                    return true;
                }
            }
        }
        //check upward diagonal
        for(int row = 3; row < grid.length; row++){
            for(int col = 0; col < grid[0].length - 3; col++){
                if (grid[row][col] == player   &&
                        grid[row-1][col+1] == player &&
                        grid[row-2][col+2] == player &&
                        grid[row-3][col+3] == player){
                    return true;
                }
            }
        }
        //check downward diagonal
        for(int row = 0; row < grid.length - 3; row++){
            for(int col = 0; col < grid[0].length - 3; col++){
                if (grid[row][col] == player   &&
                        grid[row+1][col+1] == player &&
                        grid[row+2][col+2] == player &&
                        grid[row+3][col+3] == player){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFinish(int player, int turn, boolean winner){
        // check finish game
        if (winner || turn > 42){
            if (winner) {
                if (player == 1) {
                    Glide.with(this).asGif().load(R.drawable.player1_loser).into(viewPlayer1);
                    Glide.with(this).asGif().load(R.drawable.player2_winner).into(viewPlayer2);
                } else {
                    Glide.with(this).asGif().load(R.drawable.player1_winner).into(viewPlayer1);
                    Glide.with(this).asGif().load(R.drawable.player2_loser).into(viewPlayer2);
                }
            } else {
                Glide.with(this).asGif().load(R.drawable.tie_game1).into(viewPlayer1);
                Glide.with(this).asGif().load(R.drawable.tie_game2).into(viewPlayer2);
            }
            return true;
        }
        return false;
    }

    public void resetGame(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}