package com.experis.de.MeFit;

import com.experis.de.MeFit.models.*;
import com.experis.de.MeFit.repositories.*;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


@Component
public class AppStartupRunner implements ApplicationRunner {


    private final ExerciseRepository exerciseRepository;
    private final GoalRepository goalRepository;
    private final ProfileRepository profileRepository;
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    @Autowired
    public AppStartupRunner(ExerciseRepository exerciseRepository, GoalRepository goalRepository,
                            ProfileRepository profileRepository, ProgramRepository programRepository,
                            UserRepository userRepository, WorkoutRepository workoutRepository, MuscleGroupRepository muscleGroupRepository) {
        this.exerciseRepository = exerciseRepository;
        this.goalRepository = goalRepository;
        this.profileRepository = profileRepository;
        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.muscleGroupRepository = muscleGroupRepository;
    }

    @Override
    public void run(ApplicationArguments args) {


        /*this.exerciseRepository.deleteAll();
        this.goalRepository.deleteAll();
        this.profileRepository.deleteAll();
        this.programRepository.deleteAll();
        //this.userRepository.deleteAll();
        this.workoutRepository.deleteAll();*/

        //var muscleGroupList = new HashSet<MuscleGroup>();
        var exercisesList = new HashSet<Exercise>();
        /*var workoutList = new HashSet<Workout>();
        var programList = new HashSet<Program>();*/

        MuscleGroup chest = new MuscleGroup();
        chest.setName("chest");

        MuscleGroup core = new MuscleGroup();
        core.setName("core");

        MuscleGroup legs = new MuscleGroup();
        legs.setName("legs");

        MuscleGroup back = new MuscleGroup();
        back.setName("back");


        Exercise situp = new Exercise();
        situp.setName("sit ups");
        situp.setDescription("lay down on your back and push upper body");
        situp.setMuscleGroup(chest);
        core.getExercises().add(situp);


        Exercise pushup = new Exercise();
        pushup.setName("pushups");
        pushup.setDescription("push you upper body up while lying on your belly");
        pushup.setMuscleGroup(chest);
        chest.getExercises().add(pushup);


        Exercise plank= new Exercise();
        plank.setName("plank");
        plank.setDescription("lay down on your toes and lower arms keep up as long as possible");
        plank.setMuscleGroup(core);
        core.getExercises().add(plank);


        Exercise squat= new Exercise();
        squat.setName("squat");
        squat.setDescription("stand on your feet and get your butt to the ground as far as possible and then come up again.");
        squat.setMuscleGroup(legs);
        legs.getExercises().add(squat);


        Exercise superman = new Exercise();
        superman.setName("superman");
        superman.setDescription("lay down on your chest and keep your upper back and your legs up as far as possible, hold on.");
        superman.setMuscleGroup(back);
        back.getExercises().add(superman);


        Exercise pullup= new Exercise();
        pullup.setName("pull-up");
        pullup.setDescription("pull yourself up, so your head is above your hands, hold, lower, repeat.");
        pullup.setMuscleGroup(back);
        back.getExercises().add(pullup);


        Exercise sidelegraise = new Exercise();
        sidelegraise.setName("side-legraise");
        sidelegraise.setDescription("lay down on your side, so you only touch the ground with your lower arm and your feet. raise the leg up and down.");
        sidelegraise.setMuscleGroup(legs);
        legs.getExercises().add(sidelegraise);


        Exercise squatjump = new Exercise();
        squatjump.setName("squat-jump");
        squatjump.setDescription("stand on your feet, lower your butt as far as possible and jump up while straighten up your whole body.");
        squatjump.setMuscleGroup(legs);
        legs.getExercises().add(squatjump);

       /*muscleGroupList.add(back);
        muscleGroupList.add(chest);
        muscleGroupList.add(core);
        muscleGroupList.add(legs);*/



        Workout coreburn = new Workout();
        coreburn.setName("core-burn");
        coreburn.setType("stability");
        coreburn.getExercises().add(situp);
        coreburn.getExercises().add(plank);
        coreburn.getExercises().add(sidelegraise);
        situp.getWorkouts().add(coreburn);
        plank.getWorkouts().add(coreburn);
        sidelegraise.getWorkouts().add(coreburn);
        //connect excercises with workouts

        Workout backpain = new Workout();
        backpain.setName("back-pain");
        backpain.setType("strength");
        backpain.getExercises().add(pullup);
        backpain.getExercises().add(superman);
        backpain.getExercises().add(plank);
        pullup.getWorkouts().add(backpain);
        superman.getWorkouts().add(backpain);
        plank.getWorkouts().add(backpain);
        //connect excercises with workouts

        Workout legburn = new Workout();
        legburn.setName("leg-burn");
        legburn.setType("strength");
        legburn.getExercises().add(squat);
        legburn.getExercises().add(sidelegraise);
        legburn.getExercises().add(squatjump);
        squat.getWorkouts().add(legburn);
        sidelegraise.getWorkouts().add(legburn);
        squatjump.getWorkouts().add(legburn);
        //connect excercises with workouts


        Workout chestpush= new Workout();
        chestpush.setName("chest-push");
        chestpush.setType("endurance");
        chestpush.getExercises().add(pushup);
        chestpush.getExercises().add(plank);
        pushup.getWorkouts().add(chestpush);
        plank.getWorkouts().add(chestpush);
        //connect excercises with workouts

        exercisesList.add(situp);
        exercisesList.add(sidelegraise);
        exercisesList.add(squatjump);
        exercisesList.add(squat);
        exercisesList.add(superman);
        exercisesList.add(plank);
        exercisesList.add(pullup);
        exercisesList.add(pushup);

       /* workoutList.add(coreburn);
        workoutList.add(legburn);
        workoutList.add(backpain);
        workoutList.add(chestpush);*/

        Program fatburn = new Program();
        fatburn.setName("fatburn");
        fatburn.setCategory("diet");
        /*fatburn.getWorkouts().add(chestpush);
        fatburn.getWorkouts().add(legburn);*/
        chestpush.getPrograms().add(fatburn);
        legburn.getPrograms().add(fatburn);

        Program strengthup = new Program();
        strengthup.setName("strengthup");
        strengthup.setCategory("strength");
        /*strengthup.getWorkouts().add(legburn);
        strengthup.getWorkouts().add(backpain);
        strengthup.getWorkouts().add(coreburn);*/
        legburn.getPrograms().add(strengthup);
        backpain.getPrograms().add(strengthup);
        coreburn.getPrograms().add(strengthup);

        Program endurancia = new Program();
        endurancia.setName("endurancia");
        endurancia.setCategory("endurance");
        /*endurancia.getWorkouts().add(legburn);
        endurancia.getWorkouts().add(chestpush);
        endurancia.getWorkouts().add(coreburn);*/
        legburn.getPrograms().add(endurancia);
        chestpush.getPrograms().add(endurancia);
        coreburn.getPrograms().add(endurancia);


        Goal goal1 = new Goal();
        goal1.setStartDate(new DateTime(2021, 10, 12,0,0).toDate());
        goal1.setEndDate(new DateTime(2021, 10, 19,0,0).toDate());
        goal1.setArchived(true);
        coreburn.setGoal(goal1);
        legburn.setGoal(goal1);
        endurancia.setGoal(goal1);

        Goal goal2 = new Goal();
        goal2.setStartDate(new DateTime(2021, 10, 20,0,0).toDate());
        goal2.setEndDate(new DateTime(2021, 10, 27,0,0).toDate());
        goal2.setArchived(false);
        backpain.setGoal(goal2);
        chestpush.setGoal(goal2);
        strengthup.setGoal(goal2);
        fatburn.setGoal(goal2);



        if (exerciseRepository.findAll().size()==0) {
            //muscleGroupRepository.saveAll(muscleGroupList);
            exerciseRepository.saveAll(exercisesList);
            //workoutRepository.saveAll(workoutList);
            //programRepository.saveAll(programList);
        }

    }
}
