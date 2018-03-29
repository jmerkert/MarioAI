package de.novatec.marioai.tools;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.human.CheaterKeyboardAgent;
import ch.idsia.ai.agents.human.HumanKeyboardAgent;
import ch.idsia.ai.tasks.ProgressTask;
import ch.idsia.ai.tasks.Task;
import ch.idsia.mario.engine.level.Level;
import ch.idsia.mario.engine.level.Level.LEVEL_TYPES;
import ch.idsia.mario.engine.sprites.Mario.MODE;
import ch.idsia.tools.RunnerOptions;
import competition.cig.robinbaumgarten.AStarAgent;
import de.novatec.marioai.astar.AStar_RGU;

public class MarioAiRunner {

	public static void run(Agent agent,LevelConfig levelConfig,int difficulty,int fps, boolean randomize, boolean viewable) {
		if(agent==null){
			System.err.println("Agent can't be null!\nPlease choose a valid Agent!");
			return;
		}
		if(levelConfig==null&&!randomize) System.err.println("LevelConfig is null, Level will be randomized!");
		
		RunnerOptions rOptions=new RunnerOptions(agent);
		
		rOptions.setDifficulty(difficulty);
		
		rOptions.setViewable(viewable);
		rOptions.setFPS(fps);
		rOptions.setWindowHeigth(320*6);
		rOptions.setWindowWidth(240*6);
		rOptions.setMarioStartMode(MODE.MODE_FIRE);
		//rOptions.setLevelLength(100);
		
		if(!randomize&&levelConfig!=null) {
			rOptions.setLevelSeed(levelConfig.getSeed());
			rOptions.setLevelType(levelConfig.getType());
			rOptions.setLevelLength(levelConfig.getLength());
			
		}

		Task task=new ProgressTask(rOptions);
		
		System.out.println(task.evaluteWithExtendedInfo().toString()); 
	}
	
	public static void run(Agent agent,LevelConfig levelConfig,boolean randomize) {
		if(agent==null) {
			System.err.println("Agent can't be null!\nPlease choose a valid Agent!");
			return;
		} 
		if(levelConfig==null) {
			if(!randomize){
			System.err.println("LevelConfig can only be null if randomize==true!\n Please choose a valid LevelConfig or activate Random-Level-Generation");
			return;
			}
			else run(agent, levelConfig, 0, 24, true, true);
		}
		else 
		
		run(agent, levelConfig, levelConfig.getPresetDifficulty(), 24, randomize, true);
	}
	
	public static void main(String[]args) {
		//run(new AStar_RGU(),LevelConfig.Level1,5,24,false,true);
		run(new ExampleAgent(),LevelConfig.Level1,1,48,false,false);
		run(new ExampleAgent(),LevelConfig.Level1,1,48,false,true);
		//run(new HumanKeyboardAgent(), LevelConfig.Level2,10,24,true,true);
		//run(new ExampleAgent(), null, true);
		//run(new AStarAgent(), LevelConfig.Level1,1,48,false,true);
	}
	
	public enum LevelConfig{
		Level1(797938204,256,2,LEVEL_TYPES.OVERGROUND),
		Level2(958938223,256,2,LEVEL_TYPES.CASTLE),
		Level3(1193454339,256,2,LEVEL_TYPES.OVERGROUND);
		
		private LevelConfig(int seed,int length,int presetDifficulty,LEVEL_TYPES type) {
			this.seed=seed;
			this.type=type;
			this.length=length;
			this.presetDifficulty=presetDifficulty;
		}
		
		private int seed,length,presetDifficulty;
		private LEVEL_TYPES type;
		
		public int getSeed() {
			return seed;
		}
		
		public LEVEL_TYPES getType() {
			return type;
		}
		
		public int getLength() {
			return length;
		}

		public int getPresetDifficulty() {
			return presetDifficulty;
		}
	}
	
}


