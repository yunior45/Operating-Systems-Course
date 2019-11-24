#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

/* number of matrix columns and rows */
#define M 5
#define N 3
int matrix[N][M];
int sum = 0;

/* thread function; it sums the values of the matrix in the row */
void *SumValues(void *i)
{
    int n = (int)i;      /* number of row */
    int total = 0;     /* the total of the values in the row */
    int j;

    for (j = 0; j < M; j++) /* sum values in the "n" row */
        total += matrix[n][j];

    printf("The total in row %d is %d\n", n, total);
    pthread_exit((void*)total);
}

int main(int argc, char *argv[])
{
    int i, j;

    pthread_t threads[N]; /* descriptors of threads */

    int total = 0; /* the total of the values in the matrix */

    for (i = 0; i < N; i++) /* initialize the matrix */
        for (j = 0; j < M; j++)
            matrix[i][j] = (int) rand() % 50;

    for(i = 0; i < N; i++) /* create threads */
        if (pthread_create(&threads[i], NULL, SumValues, (void *)i))
        {
            printf("Can not create a thread\n");
            exit(1);
        }

    for (i = 0; i < N; i++)
    {
        int value;
        pthread_join(threads[i], (void **)&value);
        sum += value;
    }

    printf("The total values in the matrix is %d\n", sum);
    return 0;
}
