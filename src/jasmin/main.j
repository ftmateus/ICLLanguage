.source main.j
.class public main
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V

	.limit locals 2
	.limit stack 256

	getstatic java/lang/System/out Ljava/io/PrintStream;

	new frame_0						; Frame 0 started
	dup
	invokespecial frame_0/<init>()V
	dup
	astore_1
	dup
	new frame_1						; Frame 1 started
	dup
	invokespecial frame_1/<init>()V
	dup
	aload_1
	putfield frame_1/SL Lframe_0;
	dup
	astore_1
	dup
	sipush 2
	putfield frame_1/loc_0 I
	pop								; Frame 1 finished
	aload_1							; Get z variable
	getfield frame_1/loc_0 I
	aload_1							; Get z variable
	getfield frame_1/loc_0 I
	iadd
	aload_1							; Leave nested frame
	getfield frame_1/SL Lframe_0;
	astore_1
	putfield frame_0/loc_0 I
	pop								; Frame 0 finished
	aload_1							; Get x variable
	getfield frame_0/loc_0 I
	new frame_2						; Frame 2 started
	dup
	invokespecial frame_2/<init>()V
	dup
	astore_1
	dup
	sipush 1
	putfield frame_2/loc_0 I
	pop								; Frame 2 finished
	new frame_3						; Frame 3 started
	dup
	invokespecial frame_3/<init>()V
	dup
	aload_1
	putfield frame_3/SL Lframe_2;
	dup
	astore_1
	dup
	sipush 2
	putfield frame_3/loc_0 I
	dup
	sipush 3
	putfield frame_3/loc_1 I
	pop								; Frame 3 finished
	aload_1							; Get x variable
	getfield frame_3/loc_1 I
	aload_1							; Get y variable
	getfield frame_3/loc_0 I
	iadd
	aload_1							; Leave nested frame
	getfield frame_3/SL Lframe_2;
	astore_1
	iadd
	sipush -1
	sipush 1
	imul
	sipush 1
	isub
	iadd

	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
	return

.end method