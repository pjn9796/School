/**
 * SUBMIT THIS FILE.
 * 
 * @author your name here
 */

package edu.sdsu.cs.prog3;

import java.awt.Point;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

import edu.sdsu.cs.prog2.SlowDeque;

/**
 * The MazeSolver class implements a breadth-first search algorithm to produce a
 * Deque of moves from the origin to the destination.
 * 
 * @author Your name here.
 * @account Your bitbucket user name
 *
 */
public class MazeSolver {
	
	final Maze myMaze;
	
	int[][] visited;
	
	public MazeSolver(Maze toSolve) {
		myMaze = toSolve;
		visited = new int[toSolve.size()][toSolve.size()];
	}
	
	/**
	 * STUDENTS MUST CODE: Given the starting and ending points within the maze,
	 * this method performs a breadth-first search and returns one shortest path
	 * between them. The strategy should be:
	 * 
	 * <li>Mark all rooms unvisited</li> <li>Enqueue the starting room and record
	 * the current distance as 0</li> <li><b>While</b> the queue of rooms has
	 * items:</li> <li>Dequeue a room and mark it as visited</li> <li>Mark each
	 * accessible, adjoining room as distance (current)+1 and place it in the
	 * queue</li> <p> This process will determine the number of steps one must
	 * take to return to the starting point from any room in the maze. With these
	 * data, you will then work backward to build a stack of moves. To find the
	 * shortest path, one only need to push the destination room on a stack, and
	 * then work backwards from there by selecting the connected room at each step
	 * with the shortest distance back to the origin. As you find the next step to
	 * take, push it on the stack and continue. When you reach the origin, your
	 * stack will contain the list of rooms one must visit, in sequential order,
	 * to exit the maze. </p>
	 * 
	 * @param start
	 *          The starting position in the maze. The .y component refers to the
	 *          row and comes first when referring to the grid[y][x].
	 * @param end
	 *          The destination point in the maze.
	 * @return A Deque of the rooms one must visit to reach the exit.
	 */
	public Deque<MazeRoom> solve(Point start, Point end) {
		// TODO: Code this
		markRoomsUnvisited();
		Deque<MazeRoom> queue = new SlowDeque<MazeRoom>();
		queue.push(myMaze.getRoom(start.y, start.x));
		visited[start.y][start.x] = 0;
		while(!queue.isEmpty()){
			MazeRoom room = queue.removeLast();
			final int row = room.getRow();
			final int col = room.getCol();
			int current = visited[room.getRow()][room.getCol()];
			
			//if(current !=-1)
			System.out.println("current: " + current);
			//if(!queue.isEmpty())
				//System.out.println("room: " + visited[queue.peek().getRow()][queue.peek().getCol()] );
			//System.out.println("queue size: " + queue.size());

			if(room.canMoveEast() && visited[row][col+1]<0){
				queue.add(myMaze.getRoom(row, col+1));
				visited[row][col+1] = current+1;
			}
			if(room.canMoveWest() && visited[row][col-1]<0){
				queue.add(myMaze.getRoom(row, col-1));
				visited[row][col-1] = current+1;
			}
			if(room.canMoveNorth() && visited[row+1][col]<0){
				queue.add(myMaze.getRoom(row+1, col));
				visited[row+1][col] = current+1;
			}
			if(room.canMoveSouth() && visited[row-1][col]<0){
				queue.add(myMaze.getRoom(row-1, col));
				visited[row-1][col] = current+1;
			}
		}
		
		printDistances();
		
		queue.add(myMaze.getRoom(end.y, end.x));
		boolean done = false;
		while(!done){
			System.out.println(!done);
			MazeRoom room = queue.peek();
			final int row = room.getRow();
			final int col = room.getCol();
			int current = visited[row][col];
			if(current == 1){
				//System.out.println("done");
				done = true;
			}else System.out.println("not done " + current);
			if(room.canMoveEast() && visited[row][col+1]==current-1){
				queue.push(myMaze.getRoom(row, col+1));
			}
			if(room.canMoveWest() && visited[row][col-1]==current-1){
				queue.push(myMaze.getRoom(row, col-1));
			}
			if(room.canMoveNorth() && visited[row+1][col]==current-1){
				queue.push(myMaze.getRoom(row+1, col));
			}
			if(room.canMoveSouth() && visited[row-1][col]==current-1){
				queue.push(myMaze.getRoom(row-1, col));
			}
		}
		return queue;
	}
	
	private void markRoomsUnvisited(){
		for(int i = 0; i < myMaze.size(); i++){
			for(int j = 0; j < myMaze.size(); j++){
				visited[i][j] = -1;
			}
		}
	}
	
	private void printDistances(){
		for(int i = 0; i < visited.length; i++){
			for(int j = 0; j < visited.length; j++){
				System.out.print(" " + visited[i][j]);
			}
			System.out.println();
		}
	}
}
