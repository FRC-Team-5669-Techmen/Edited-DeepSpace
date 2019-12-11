/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  TalonSRX wristMotor = new TalonSRX(3);
  TalonSRX liftMotor = new TalonSRX(5);
  TalonSRX backRightMotor = new TalonSRX(10);
  TalonSRX backLeftMotor = new TalonSRX(8);
  TalonSRX frontRightMotor = new TalonSRX(6);
  TalonSRX frontLeftMotor = new TalonSRX(1);
  TalonSRX wheelOfDeathjpg = new TalonSRX(2);
  TalonSRX wheelOfDeath2jpg = new TalonSRX(7);
  Joystick jStick = new Joystick(0);
  Joystick bStick = new Joystick(2);
  double speedAddition = 1.0;
  DoubleSolenoid solenoid = new DoubleSolenoid(2, 3);
  boolean solenoidState = false;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    liftMotor.setInverted(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  
  public void teleopPeriodic() {
    //SIDE TO SIDE
    //while(jStick.getRawAxis(4) == 1){
      backRightMotor.set(ControlMode.PercentOutput, (((jStick.getRawAxis(4) / 3) * -0.75) + ((jStick.getRawAxis(1) / 3) * -0.7) + ((jStick.getRawAxis(0) / 3) * 0.7)) * speedAddition );      //^  V    RFB
      backLeftMotor.set(ControlMode.PercentOutput, (((jStick.getRawAxis(4) / 3) * 0.6) + ((jStick.getRawAxis(1) / 3) * -0.6) +((jStick.getRawAxis(0) / 3) * -0.6)) * speedAddition );        //V  ^   RBF
      frontRightMotor.set(ControlMode.PercentOutput, (((jStick.getRawAxis(4) / 3) * 0.6) + ((jStick.getRawAxis(1) / 3) * 0.6) +  ((jStick.getRawAxis(0) / 3) * 0.6))* speedAddition );
      frontLeftMotor.set(ControlMode.PercentOutput, (((jStick.getRawAxis(4) / 3) * 0.6) + ((jStick.getRawAxis(1) / 3) * -0.6) + ((jStick.getRawAxis(0) / 3) * 0.6)) * speedAddition );
    //}
    
    
    if (bStick.getRawButtonPressed(9)) {
      solenoidState = !solenoidState;
      if (solenoidState){
        solenoid.set(Value.kForward);
      }
      else {
        solenoid.set(Value.kReverse);
      }
    }

    /*  test code after open house
    while(bStick.getRawButtonPressed(1)) {
      liftMotor.set(ControlMode.PercentOutput, 0.3 );
    }

    while(bStick.getRawButtonPressed(2)){
      liftMotor.set(ControlMode.PercentOutput, -0.3 )
    }

    if(bStick.getRawButtonPressed(3)){
      liftMotor.set(ControlMode.PercentOutput, 0 );
    }

    */

    if(bStick.getRawButton(1)){
      liftMotor.set(ControlMode.PercentOutput, 0.5 );
    }else if(bStick.getRawButton(2)){
      liftMotor.set(ControlMode.PercentOutput, -0.5 );
    }else {
      liftMotor.set(ControlMode.PercentOutput, 0 );
    }
    if(bStick.getRawButton(5)){
      wristMotor.set(ControlMode.PercentOutput, 0.5 );
    }else if(bStick.getRawButton(6)){
      wristMotor.set(ControlMode.PercentOutput, -0.5 );
    }else {
      wristMotor.set(ControlMode.PercentOutput, 0 );
    }
    if(bStick.getRawButton(3)){
      wheelOfDeathjpg.set(ControlMode.PercentOutput, 0.70 );
      wheelOfDeath2jpg.set(ControlMode.PercentOutput, -0.70 );
    }else if(bStick.getRawButton(7)){
      wheelOfDeathjpg.set(ControlMode.PercentOutput, -0.70 );
      wheelOfDeath2jpg.set(ControlMode.PercentOutput, 0.70 );
    }else{
      wheelOfDeathjpg.set(ControlMode.PercentOutput, 0 );
      wheelOfDeath2jpg.set(ControlMode.PercentOutput, 0 );
    }
    if(bStick.getRawButton(11) && bStick.getRawButton(10)){
      System.out.println("11 and 10");
      speedAddition = 4;
    }else if(bStick.getRawButton(11)){
      System.out.println("11");
      speedAddition = 3;
    }else if(bStick.getRawButton(10)){
      System.out.println("10");
      speedAddition = 2;
    }else{
      System.out.println("not 10 or 11");
      speedAddition = 1;
    }
  }
  

  /**
   * This function is called periodically during test mode.
*/
  @Override
  public void testPeriodic() {
  }
}


// pneumatics
// team358.org/