// Libraries for the program
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>

//Defined variables for the values used with the pipe function
#define READ 0
#define WRITE 1

//initialized global variables
int result = 0;
int value = 0;

int main() {

    /*
     * This block of code is intended to initialize the clock function and start
     * it to determine how much time it took for the program to execute. Is not
     * needed per the instructions but i included it to match the desired output.
     */

    clock_t clock(void);
    clock_t start, end, total;
    start = clock();

    /*
     * Initialization of the pipe function to communicate between the parent and
     * child process.
     */

    int fd[2];
    pipe(fd);

    /*
     * User input is scanned from the command line and stored within the global
     * variable "value".
     */

    printf("Please enter an Integer: ");
    scanf("%d", &value);

    /*
     * Error checking code block where the program checks if the input entered
     * is valid. This if statement checks if the value entered is either a string
     * or a negative number.
     */

    if (value < 0 || !value){
        printf("Invalid input!\n");
        return 0;
    }

    /*
     * Parent process calls the fork function here and creates a child process. The PID
     * of the child process is assigned to the parent and the PID of the child becomes
     * 0. The child process calculates the factorial of the value entered and via the pipe
     * function, the child process writes the resulting value of result to the parent process.
     * The parent process then reads the value passed through the pipe and assigns that value
     * to the result global variable in the parent process. The parent then prints that
     * value to the users screen.
     */

   int val = fork();
    if (val == 0){

        // for loop which calculates the factorial within the child process.
        for(int i = value; i > 1; i--){
            if(result == 0){
                result = i * (i - 1);
            } else {
                result = result * (i - 1);
            }
        }

        /*
         * Child process side pipe communication between the parent and child process. Since
         * the child does not need to read data passed though the pipe, the best best practice is
         * to close its communication. This also applies after finishing any other communications
         * via pipes.
         */

        close(fd[READ]);
        write(fd[WRITE], &result, sizeof(result));
        close(fd[WRITE]);

    } else {

        /*
         * wait function is called here in the parent process to allow the child to calculate
         * the factorial in the child process and pass it through the pipe. final calculated
         * value is assigned to the global variable "Result".
         */

        wait(NULL);

        // Parent process side pipe communications between child and parent process.
        close(fd[WRITE]);
        read(fd[READ], &result, sizeof(result));
        close(fd[READ]);

        // Stop the timer and calculate the resulting time the process took.
        end = clock();
        total = (double)(clock() - end);

        /*
         * Results are printed in the parent process to be displayed to the user. The
         * time the program took to execute is also printed (Not needed for assignment
         * but i wanted to match the desired example exactly).
         */

        printf("Factorial of %d = %d\n", value, result);
        printf("RUN SUCCESSFUL (total time: %lds)\n", total);
    }
    return 0;
}
