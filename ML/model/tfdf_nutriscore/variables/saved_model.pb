��
��
^
AssignVariableOp
resource
value"dtype"
dtypetype"
validate_shapebool( �
8
Const
output"dtype"
valuetensor"
dtypetype
$
DisableCopyOnRead
resource�
.
Identity

input"T
output"T"	
Ttype
�
MergeV2Checkpoints
checkpoint_prefixes
destination_prefix"
delete_old_dirsbool("
allow_missing_filesbool( �

NoOp
M
Pack
values"T*N
output"T"
Nint(0"	
Ttype"
axisint 
�
PartitionedCall
args2Tin
output2Tout"
Tin
list(type)("
Tout
list(type)("	
ffunc"
configstring "
config_protostring "
executor_typestring 
C
Placeholder
output"dtype"
dtypetype"
shapeshape:
@
ReadVariableOp
resource
value"dtype"
dtypetype�
o
	RestoreV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0�
l
SaveV2

prefix
tensor_names
shape_and_slices
tensors2dtypes"
dtypes
list(type)(0�
?
Select
	condition

t"T
e"T
output"T"	
Ttype
H
ShardedFilename
basename	
shard

num_shards
filename
f
SimpleMLCreateModelResource
model_handle"
	containerstring "
shared_namestring �
�
SimpleMLInferenceOpWithHandle
numerical_features
boolean_features
categorical_int_features'
#categorical_set_int_features_values1
-categorical_set_int_features_row_splits_dim_1	1
-categorical_set_int_features_row_splits_dim_2	
model_handle
dense_predictions
dense_col_representation"
dense_output_dimint(0�
�
#SimpleMLLoadModelFromPathWithHandle
model_handle
path" 
output_typeslist(string)
 "
file_prefixstring " 
allow_slow_inferencebool(�
�
StatefulPartitionedCall
args2Tin
output2Tout"
Tin
list(type)("
Tout
list(type)("	
ffunc"
configstring "
config_protostring "
executor_typestring ��
@
StaticRegexFullMatch	
input

output
"
patternstring
m
StaticRegexReplace	
input

output"
patternstring"
rewritestring"
replace_globalbool(
L

StringJoin
inputs*N

output"

Nint("
	separatorstring 
�
VarHandleOp
resource"
	containerstring "
shared_namestring "

debug_namestring "
dtypetype"
shapeshape"#
allowed_deviceslist(string)
 �
9
VarIsInitializedOp
resource
is_initialized
�"serve*2.18.02v2.18.0-rc2-4-g6550e4bd8028��
W
asset_path_initializerPlaceholder*
_output_shapes
: *
dtype0*
shape: 
�
VariableVarHandleOp*
_class
loc:@Variable*
_output_shapes
: *

debug_name	Variable/*
dtype0*
shape: *
shared_name
Variable
a
)Variable/IsInitialized/VarIsInitializedOpVarIsInitializedOpVariable*
_output_shapes
: 
z
Variable/AssignAssignVariableOpVariableasset_path_initializer*&
 _has_manual_control_dependencies(*
dtype0
]
Variable/Read/ReadVariableOpReadVariableOpVariable*
_output_shapes
: *
dtype0
Y
asset_path_initializer_1Placeholder*
_output_shapes
: *
dtype0*
shape: 
�

Variable_1VarHandleOp*
_class
loc:@Variable_1*
_output_shapes
: *

debug_nameVariable_1/*
dtype0*
shape: *
shared_name
Variable_1
e
+Variable_1/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_1*
_output_shapes
: 
�
Variable_1/AssignAssignVariableOp
Variable_1asset_path_initializer_1*&
 _has_manual_control_dependencies(*
dtype0
a
Variable_1/Read/ReadVariableOpReadVariableOp
Variable_1*
_output_shapes
: *
dtype0
Y
asset_path_initializer_2Placeholder*
_output_shapes
: *
dtype0*
shape: 
�

Variable_2VarHandleOp*
_class
loc:@Variable_2*
_output_shapes
: *

debug_nameVariable_2/*
dtype0*
shape: *
shared_name
Variable_2
e
+Variable_2/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_2*
_output_shapes
: 
�
Variable_2/AssignAssignVariableOp
Variable_2asset_path_initializer_2*&
 _has_manual_control_dependencies(*
dtype0
a
Variable_2/Read/ReadVariableOpReadVariableOp
Variable_2*
_output_shapes
: *
dtype0
Y
asset_path_initializer_3Placeholder*
_output_shapes
: *
dtype0*
shape: 
�

Variable_3VarHandleOp*
_class
loc:@Variable_3*
_output_shapes
: *

debug_nameVariable_3/*
dtype0*
shape: *
shared_name
Variable_3
e
+Variable_3/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_3*
_output_shapes
: 
�
Variable_3/AssignAssignVariableOp
Variable_3asset_path_initializer_3*&
 _has_manual_control_dependencies(*
dtype0
a
Variable_3/Read/ReadVariableOpReadVariableOp
Variable_3*
_output_shapes
: *
dtype0
Y
asset_path_initializer_4Placeholder*
_output_shapes
: *
dtype0*
shape: 
�

Variable_4VarHandleOp*
_class
loc:@Variable_4*
_output_shapes
: *

debug_nameVariable_4/*
dtype0*
shape: *
shared_name
Variable_4
e
+Variable_4/IsInitialized/VarIsInitializedOpVarIsInitializedOp
Variable_4*
_output_shapes
: 
�
Variable_4/AssignAssignVariableOp
Variable_4asset_path_initializer_4*&
 _has_manual_control_dependencies(*
dtype0
a
Variable_4/Read/ReadVariableOpReadVariableOp
Variable_4*
_output_shapes
: *
dtype0
v
countVarHandleOp*
_output_shapes
: *

debug_namecount/*
dtype0*
shape: *
shared_namecount
W
count/Read/ReadVariableOpReadVariableOpcount*
_output_shapes
: *
dtype0
v
totalVarHandleOp*
_output_shapes
: *

debug_nametotal/*
dtype0*
shape: *
shared_nametotal
W
total/Read/ReadVariableOpReadVariableOptotal*
_output_shapes
: *
dtype0
|
count_1VarHandleOp*
_output_shapes
: *

debug_name
count_1/*
dtype0*
shape: *
shared_name	count_1
[
count_1/Read/ReadVariableOpReadVariableOpcount_1*
_output_shapes
: *
dtype0
|
total_1VarHandleOp*
_output_shapes
: *

debug_name
total_1/*
dtype0*
shape: *
shared_name	total_1
[
total_1/Read/ReadVariableOpReadVariableOptotal_1*
_output_shapes
: *
dtype0
�
SimpleMLCreateModelResourceSimpleMLCreateModelResource*
_output_shapes
: *E
shared_name64simple_ml_model_2a2dac39-2c2e-4377-ae7f-9261a2740648
�
learning_rateVarHandleOp*
_output_shapes
: *

debug_namelearning_rate/*
dtype0*
shape: *
shared_namelearning_rate
g
!learning_rate/Read/ReadVariableOpReadVariableOplearning_rate*
_output_shapes
: *
dtype0
�
	iterationVarHandleOp*
_output_shapes
: *

debug_name
iteration/*
dtype0	*
shape: *
shared_name	iteration
_
iteration/Read/ReadVariableOpReadVariableOp	iteration*
_output_shapes
: *
dtype0	
�

is_trainedVarHandleOp*
_output_shapes
: *

debug_nameis_trained/*
dtype0
*
shape: *
shared_name
is_trained
a
is_trained/Read/ReadVariableOpReadVariableOp
is_trained*
_output_shapes
: *
dtype0

{
 serving_default_energy-kcal_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
u
serving_default_fiber_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
�
Eserving_default_fruits-vegetables-nuts-estimate-from-ingredients_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
x
serving_default_proteins_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
t
serving_default_salt_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
}
"serving_default_saturated-fat_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
v
serving_default_sugars_100gPlaceholder*#
_output_shapes
:���������*
dtype0*
shape:���������
�
StatefulPartitionedCallStatefulPartitionedCall serving_default_energy-kcal_100gserving_default_fiber_100gEserving_default_fruits-vegetables-nuts-estimate-from-ingredients_100gserving_default_proteins_100gserving_default_salt_100g"serving_default_saturated-fat_100gserving_default_sugars_100gSimpleMLCreateModelResource*
Tin

2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *-
f(R&
$__inference_signature_wrapper_192487
a
ReadVariableOpReadVariableOpVariable^Variable/Assign*
_output_shapes
: *
dtype0
�
StatefulPartitionedCall_1StatefulPartitionedCallReadVariableOpSimpleMLCreateModelResource*
Tin
2*
Tout
2*
_collective_manager_ids
 *&
 _has_manual_control_dependencies(*
_output_shapes
: * 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *(
f#R!
__inference__initializer_192498
�
NoOpNoOp^StatefulPartitionedCall_1^Variable/Assign^Variable_1/Assign^Variable_2/Assign^Variable_3/Assign^Variable_4/Assign
�
ConstConst"/device:CPU:0*
_output_shapes
: *
dtype0*�
value�B� B�
�
	variables
trainable_variables
regularization_losses
	keras_api
__call__
*&call_and_return_all_conditional_losses
_default_save_signature

_multitask
	_is_trained

_learner_params
	_features
	optimizer
loss
_models
_build_normalized_inputs
_finalize_predictions
call
call_get_leaves
yggdrasil_model_path_tensor

signatures*

	0*
* 
* 
�
non_trainable_variables

layers
metrics
layer_regularization_losses
layer_metrics
	variables
trainable_variables
regularization_losses
__call__
_default_save_signature
*&call_and_return_all_conditional_losses
&"call_and_return_conditional_losses*

trace_0
trace_1* 

trace_0
trace_1* 
* 
* 
JD
VARIABLE_VALUE
is_trained&_is_trained/.ATTRIBUTES/VARIABLE_VALUE*
* 
* 
O

_variables
_iterations
 _learning_rate
!_update_step_xla*
* 
	
"0* 

#trace_0* 

$trace_0* 

%trace_0* 
* 

&trace_0* 

'serving_default* 

	0*
* 

(0
)1*
* 
* 
* 
* 
* 
* 

0*
SM
VARIABLE_VALUE	iteration0optimizer/_iterations/.ATTRIBUTES/VARIABLE_VALUE*
ZT
VARIABLE_VALUElearning_rate3optimizer/_learning_rate/.ATTRIBUTES/VARIABLE_VALUE*
* 
+
*_input_builder
+_compiled_model* 
* 
* 
* 

,	capture_0* 
* 
8
-	variables
.	keras_api
	/total
	0count*
H
1	variables
2	keras_api
	3total
	4count
5
_fn_kwargs*
P
6_feature_name_to_idx
7	_init_ops
#8categorical_str_to_int_hashmaps* 
S
9_model_loader
:_create_resource
;_initialize
<_destroy_resource* 
* 

/0
01*

-	variables*
UO
VARIABLE_VALUEtotal_14keras_api/metrics/0/total/.ATTRIBUTES/VARIABLE_VALUE*
UO
VARIABLE_VALUEcount_14keras_api/metrics/0/count/.ATTRIBUTES/VARIABLE_VALUE*

30
41*

1	variables*
SM
VARIABLE_VALUEtotal4keras_api/metrics/1/total/.ATTRIBUTES/VARIABLE_VALUE*
SM
VARIABLE_VALUEcount4keras_api/metrics/1/count/.ATTRIBUTES/VARIABLE_VALUE*
* 
* 
* 
* 
5
=_output_types
>
_all_files
,
_done_file* 

?trace_0* 

@trace_0* 

Atrace_0* 
* 
%
B0
C1
,2
D3
E4* 
* 

,	capture_0* 
* 
* 
* 
* 
* 
O
saver_filenamePlaceholder*
_output_shapes
: *
dtype0*
shape: 
�
StatefulPartitionedCall_2StatefulPartitionedCallsaver_filename
is_trained	iterationlearning_ratetotal_1count_1totalcountConst*
Tin
2	*
Tout
2*
_collective_manager_ids
 *
_output_shapes
: * 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *(
f#R!
__inference__traced_save_192595
�
StatefulPartitionedCall_3StatefulPartitionedCallsaver_filename
is_trained	iterationlearning_ratetotal_1count_1totalcount*
Tin

2*
Tout
2*
_collective_manager_ids
 *
_output_shapes
: * 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *+
f&R$
"__inference__traced_restore_192625�
�
-
__inference__destroyer_192502
identityG
ConstConst*
_output_shapes
: *
dtype0*
value	B :E
IdentityIdentityConst:output:0*
T0*
_output_shapes
: "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*
_input_shapes 
�
�
!__inference__wrapped_model_192391
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g)
%gradient_boosted_trees_model_1_192387
identity��6gradient_boosted_trees_model_1/StatefulPartitionedCall�
6gradient_boosted_trees_model_1/StatefulPartitionedCallStatefulPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100g%gradient_boosted_trees_model_1_192387*
Tin

2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J * 
fR
__inference_call_191621�
IdentityIdentity?gradient_boosted_trees_model_1/StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������[
NoOpNoOp7^gradient_boosted_trees_model_1/StatefulPartitionedCall*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 2p
6gradient_boosted_trees_model_1/StatefulPartitionedCall6gradient_boosted_trees_model_1/StatefulPartitionedCall:&"
 
_user_specified_name192387:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�
�
+__inference__build_normalized_inputs_191597

inputs
inputs_5
inputs_4
inputs_6
inputs_3
inputs_2
inputs_1
identity

identity_1

identity_2

identity_3

identity_4

identity_5

identity_6J
IdentityIdentityinputs*
T0*#
_output_shapes
:���������N

Identity_1Identityinputs_5*
T0*#
_output_shapes
:���������N

Identity_2Identityinputs_4*
T0*#
_output_shapes
:���������N

Identity_3Identityinputs_6*
T0*#
_output_shapes
:���������N

Identity_4Identityinputs_3*
T0*#
_output_shapes
:���������N

Identity_5Identityinputs_2*
T0*#
_output_shapes
:���������N

Identity_6Identityinputs_1*
T0*#
_output_shapes
:���������"!

identity_1Identity_1:output:0"!

identity_2Identity_2:output:0"!

identity_3Identity_3:output:0"!

identity_4Identity_4:output:0"!

identity_5Identity_5:output:0"!

identity_6Identity_6:output:0"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*|
_input_shapesk
i:���������:���������:���������:���������:���������:���������:���������:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:K G
#
_output_shapes
:���������
 
_user_specified_nameinputs
�
\
.__inference_yggdrasil_model_path_tensor_191761
staticregexreplace_input
identity�
StaticRegexReplaceStaticRegexReplacestaticregexreplace_input*
_output_shapes
: *!
pattern2d240996bcaf49d4done*
rewrite R
IdentityIdentityStaticRegexReplace:output:0*
T0*
_output_shapes
: "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*
_input_shapes
: : 

_output_shapes
: 
�
�
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192458
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g
unknown
identity��StatefulPartitionedCall�
StatefulPartitionedCallStatefulPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100gunknown*
Tin

2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *c
f^R\
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192418o
IdentityIdentity StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������<
NoOpNoOp^StatefulPartitionedCall*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 22
StatefulPartitionedCallStatefulPartitionedCall:&"
 
_user_specified_name192454:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�
�
$__inference_signature_wrapper_192487
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g
unknown
identity��StatefulPartitionedCall�
StatefulPartitionedCallStatefulPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100gunknown*
Tin

2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J **
f%R#
!__inference__wrapped_model_192391o
IdentityIdentity StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������<
NoOpNoOp^StatefulPartitionedCall*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 22
StatefulPartitionedCallStatefulPartitionedCall:&"
 
_user_specified_name192483:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�
�
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192418
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g
inference_op_model_handle
identity��inference_op�
PartitionedCallPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100g*
Tin
	2*
Tout
	2*
_collective_manager_ids
 *}
_output_shapesk
i:���������:���������:���������:���������:���������:���������:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *4
f/R-
+__inference__build_normalized_inputs_191597�
stackPackPartitionedCall:output:0PartitionedCall:output:1PartitionedCall:output:2PartitionedCall:output:3PartitionedCall:output:4PartitionedCall:output:5PartitionedCall:output:6*
N*
T0*'
_output_shapes
:���������*

axisL
ConstConst*
_output_shapes
:  *
dtype0*
value
B  N
Const_1Const*
_output_shapes
:  *
dtype0*
value
B  X
RaggedConstant/valuesConst*
_output_shapes
: *
dtype0*
valueB ^
RaggedConstant/ConstConst*
_output_shapes
:*
dtype0	*
valueB	R `
RaggedConstant/Const_1Const*
_output_shapes
:*
dtype0	*
valueB	R �
inference_opSimpleMLInferenceOpWithHandlestack:output:0Const:output:0Const_1:output:0RaggedConstant/values:output:0RaggedConstant/Const:output:0RaggedConstant/Const_1:output:0inference_op_model_handle*-
_output_shapes
:���������:*
dense_output_dim�
PartitionedCall_1PartitionedCall inference_op:dense_predictions:0'inference_op:dense_col_representation:0*
Tin
2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *1
f,R*
(__inference__finalize_predictions_191618i
IdentityIdentityPartitionedCall_1:output:0^NoOp*
T0*'
_output_shapes
:���������1
NoOpNoOp^inference_op*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 2
inference_opinference_op:,(
&
_user_specified_namemodel_handle:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�=
�
__inference__traced_save_192595
file_prefix+
!read_disablecopyonread_is_trained:
 ,
"read_1_disablecopyonread_iteration:	 0
&read_2_disablecopyonread_learning_rate: *
 read_3_disablecopyonread_total_1: *
 read_4_disablecopyonread_count_1: (
read_5_disablecopyonread_total: (
read_6_disablecopyonread_count: 
savev2_const
identity_15��MergeV2Checkpoints�Read/DisableCopyOnRead�Read/ReadVariableOp�Read_1/DisableCopyOnRead�Read_1/ReadVariableOp�Read_2/DisableCopyOnRead�Read_2/ReadVariableOp�Read_3/DisableCopyOnRead�Read_3/ReadVariableOp�Read_4/DisableCopyOnRead�Read_4/ReadVariableOp�Read_5/DisableCopyOnRead�Read_5/ReadVariableOp�Read_6/DisableCopyOnRead�Read_6/ReadVariableOpw
StaticRegexFullMatchStaticRegexFullMatchfile_prefix"/device:CPU:**
_output_shapes
: *
pattern
^s3://.*Z
ConstConst"/device:CPU:**
_output_shapes
: *
dtype0*
valueB B.parta
Const_1Const"/device:CPU:**
_output_shapes
: *
dtype0*
valueB B
_temp/part�
SelectSelectStaticRegexFullMatch:output:0Const:output:0Const_1:output:0"/device:CPU:**
T0*
_output_shapes
: f

StringJoin
StringJoinfile_prefixSelect:output:0"/device:CPU:**
N*
_output_shapes
: d
Read/DisableCopyOnReadDisableCopyOnRead!read_disablecopyonread_is_trained*
_output_shapes
 �
Read/ReadVariableOpReadVariableOp!read_disablecopyonread_is_trained^Read/DisableCopyOnRead*
_output_shapes
: *
dtype0
R
IdentityIdentityRead/ReadVariableOp:value:0*
T0
*
_output_shapes
: Y

Identity_1IdentityIdentity:output:0"/device:CPU:0*
T0
*
_output_shapes
: g
Read_1/DisableCopyOnReadDisableCopyOnRead"read_1_disablecopyonread_iteration*
_output_shapes
 �
Read_1/ReadVariableOpReadVariableOp"read_1_disablecopyonread_iteration^Read_1/DisableCopyOnRead*
_output_shapes
: *
dtype0	V

Identity_2IdentityRead_1/ReadVariableOp:value:0*
T0	*
_output_shapes
: [

Identity_3IdentityIdentity_2:output:0"/device:CPU:0*
T0	*
_output_shapes
: k
Read_2/DisableCopyOnReadDisableCopyOnRead&read_2_disablecopyonread_learning_rate*
_output_shapes
 �
Read_2/ReadVariableOpReadVariableOp&read_2_disablecopyonread_learning_rate^Read_2/DisableCopyOnRead*
_output_shapes
: *
dtype0V

Identity_4IdentityRead_2/ReadVariableOp:value:0*
T0*
_output_shapes
: [

Identity_5IdentityIdentity_4:output:0"/device:CPU:0*
T0*
_output_shapes
: e
Read_3/DisableCopyOnReadDisableCopyOnRead read_3_disablecopyonread_total_1*
_output_shapes
 �
Read_3/ReadVariableOpReadVariableOp read_3_disablecopyonread_total_1^Read_3/DisableCopyOnRead*
_output_shapes
: *
dtype0V

Identity_6IdentityRead_3/ReadVariableOp:value:0*
T0*
_output_shapes
: [

Identity_7IdentityIdentity_6:output:0"/device:CPU:0*
T0*
_output_shapes
: e
Read_4/DisableCopyOnReadDisableCopyOnRead read_4_disablecopyonread_count_1*
_output_shapes
 �
Read_4/ReadVariableOpReadVariableOp read_4_disablecopyonread_count_1^Read_4/DisableCopyOnRead*
_output_shapes
: *
dtype0V

Identity_8IdentityRead_4/ReadVariableOp:value:0*
T0*
_output_shapes
: [

Identity_9IdentityIdentity_8:output:0"/device:CPU:0*
T0*
_output_shapes
: c
Read_5/DisableCopyOnReadDisableCopyOnReadread_5_disablecopyonread_total*
_output_shapes
 �
Read_5/ReadVariableOpReadVariableOpread_5_disablecopyonread_total^Read_5/DisableCopyOnRead*
_output_shapes
: *
dtype0W
Identity_10IdentityRead_5/ReadVariableOp:value:0*
T0*
_output_shapes
: ]
Identity_11IdentityIdentity_10:output:0"/device:CPU:0*
T0*
_output_shapes
: c
Read_6/DisableCopyOnReadDisableCopyOnReadread_6_disablecopyonread_count*
_output_shapes
 �
Read_6/ReadVariableOpReadVariableOpread_6_disablecopyonread_count^Read_6/DisableCopyOnRead*
_output_shapes
: *
dtype0W
Identity_12IdentityRead_6/ReadVariableOp:value:0*
T0*
_output_shapes
: ]
Identity_13IdentityIdentity_12:output:0"/device:CPU:0*
T0*
_output_shapes
: L

num_shardsConst*
_output_shapes
: *
dtype0*
value	B :f
ShardedFilename/shardConst"/device:CPU:0*
_output_shapes
: *
dtype0*
value	B : �
ShardedFilenameShardedFilenameStringJoin:output:0ShardedFilename/shard:output:0num_shards:output:0"/device:CPU:0*
_output_shapes
: �
SaveV2/tensor_namesConst"/device:CPU:0*
_output_shapes
:*
dtype0*�
value�B�B&_is_trained/.ATTRIBUTES/VARIABLE_VALUEB0optimizer/_iterations/.ATTRIBUTES/VARIABLE_VALUEB3optimizer/_learning_rate/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/0/total/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/0/count/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/1/total/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/1/count/.ATTRIBUTES/VARIABLE_VALUEB_CHECKPOINTABLE_OBJECT_GRAPH}
SaveV2/shape_and_slicesConst"/device:CPU:0*
_output_shapes
:*
dtype0*#
valueBB B B B B B B B �
SaveV2SaveV2ShardedFilename:filename:0SaveV2/tensor_names:output:0 SaveV2/shape_and_slices:output:0Identity_1:output:0Identity_3:output:0Identity_5:output:0Identity_7:output:0Identity_9:output:0Identity_11:output:0Identity_13:output:0savev2_const"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtypes

2
	�
&MergeV2Checkpoints/checkpoint_prefixesPackShardedFilename:filename:0^SaveV2"/device:CPU:0*
N*
T0*
_output_shapes
:�
MergeV2CheckpointsMergeV2Checkpoints/MergeV2Checkpoints/checkpoint_prefixes:output:0file_prefix"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 i
Identity_14Identityfile_prefix^MergeV2Checkpoints"/device:CPU:0*
T0*
_output_shapes
: U
Identity_15IdentityIdentity_14:output:0^NoOp*
T0*
_output_shapes
: �
NoOpNoOp^MergeV2Checkpoints^Read/DisableCopyOnRead^Read/ReadVariableOp^Read_1/DisableCopyOnRead^Read_1/ReadVariableOp^Read_2/DisableCopyOnRead^Read_2/ReadVariableOp^Read_3/DisableCopyOnRead^Read_3/ReadVariableOp^Read_4/DisableCopyOnRead^Read_4/ReadVariableOp^Read_5/DisableCopyOnRead^Read_5/ReadVariableOp^Read_6/DisableCopyOnRead^Read_6/ReadVariableOp*
_output_shapes
 "#
identity_15Identity_15:output:0*(
_construction_contextkEagerRuntime*%
_input_shapes
: : : : : : : : : 2(
MergeV2CheckpointsMergeV2Checkpoints20
Read/DisableCopyOnReadRead/DisableCopyOnRead2*
Read/ReadVariableOpRead/ReadVariableOp24
Read_1/DisableCopyOnReadRead_1/DisableCopyOnRead2.
Read_1/ReadVariableOpRead_1/ReadVariableOp24
Read_2/DisableCopyOnReadRead_2/DisableCopyOnRead2.
Read_2/ReadVariableOpRead_2/ReadVariableOp24
Read_3/DisableCopyOnReadRead_3/DisableCopyOnRead2.
Read_3/ReadVariableOpRead_3/ReadVariableOp24
Read_4/DisableCopyOnReadRead_4/DisableCopyOnRead2.
Read_4/ReadVariableOpRead_4/ReadVariableOp24
Read_5/DisableCopyOnReadRead_5/DisableCopyOnRead2.
Read_5/ReadVariableOpRead_5/ReadVariableOp24
Read_6/DisableCopyOnReadRead_6/DisableCopyOnRead2.
Read_6/ReadVariableOpRead_6/ReadVariableOp:=9

_output_shapes
: 

_user_specified_nameConst:%!

_user_specified_namecount:%!

_user_specified_nametotal:'#
!
_user_specified_name	count_1:'#
!
_user_specified_name	total_1:-)
'
_user_specified_namelearning_rate:)%
#
_user_specified_name	iteration:*&
$
_user_specified_name
is_trained:C ?

_output_shapes
: 
%
_user_specified_namefile_prefix
�
L
__inference__creator_192491
identity��SimpleMLCreateModelResource�
SimpleMLCreateModelResourceSimpleMLCreateModelResource*
_output_shapes
: *E
shared_name64simple_ml_model_2a2dac39-2c2e-4377-ae7f-9261a2740648h
IdentityIdentity*SimpleMLCreateModelResource:model_handle:0^NoOp*
T0*
_output_shapes
: @
NoOpNoOp^SimpleMLCreateModelResource*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*
_input_shapes 2:
SimpleMLCreateModelResourceSimpleMLCreateModelResource
�
�
+__inference__build_normalized_inputs_191724
inputs_energy_kcal_100g
inputs_fiber_100g@
<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g
inputs_proteins_100g
inputs_salt_100g
inputs_saturated_fat_100g
inputs_sugars_100g
identity

identity_1

identity_2

identity_3

identity_4

identity_5

identity_6[
IdentityIdentityinputs_energy_kcal_100g*
T0*#
_output_shapes
:���������W

Identity_1Identityinputs_fiber_100g*
T0*#
_output_shapes
:����������

Identity_2Identity<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g*
T0*#
_output_shapes
:���������Z

Identity_3Identityinputs_proteins_100g*
T0*#
_output_shapes
:���������V

Identity_4Identityinputs_salt_100g*
T0*#
_output_shapes
:���������_

Identity_5Identityinputs_saturated_fat_100g*
T0*#
_output_shapes
:���������X

Identity_6Identityinputs_sugars_100g*
T0*#
_output_shapes
:���������"!

identity_1Identity_1:output:0"!

identity_2Identity_2:output:0"!

identity_3Identity_3:output:0"!

identity_4Identity_4:output:0"!

identity_5Identity_5:output:0"!

identity_6Identity_6:output:0"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*|
_input_shapesk
i:���������:���������:���������:���������:���������:���������:���������:WS
#
_output_shapes
:���������
,
_user_specified_nameinputs_sugars_100g:^Z
#
_output_shapes
:���������
3
_user_specified_nameinputs_saturated_fat_100g:UQ
#
_output_shapes
:���������
*
_user_specified_nameinputs_salt_100g:YU
#
_output_shapes
:���������
.
_user_specified_nameinputs_proteins_100g:�}
#
_output_shapes
:���������
V
_user_specified_name><inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g:VR
#
_output_shapes
:���������
+
_user_specified_nameinputs_fiber_100g:\ X
#
_output_shapes
:���������
1
_user_specified_nameinputs_energy_kcal_100g
�
�
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192445
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g
inference_op_model_handle
identity��inference_op�
PartitionedCallPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100g*
Tin
	2*
Tout
	2*
_collective_manager_ids
 *}
_output_shapesk
i:���������:���������:���������:���������:���������:���������:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *4
f/R-
+__inference__build_normalized_inputs_191597�
stackPackPartitionedCall:output:0PartitionedCall:output:1PartitionedCall:output:2PartitionedCall:output:3PartitionedCall:output:4PartitionedCall:output:5PartitionedCall:output:6*
N*
T0*'
_output_shapes
:���������*

axisL
ConstConst*
_output_shapes
:  *
dtype0*
value
B  N
Const_1Const*
_output_shapes
:  *
dtype0*
value
B  X
RaggedConstant/valuesConst*
_output_shapes
: *
dtype0*
valueB ^
RaggedConstant/ConstConst*
_output_shapes
:*
dtype0	*
valueB	R `
RaggedConstant/Const_1Const*
_output_shapes
:*
dtype0	*
valueB	R �
inference_opSimpleMLInferenceOpWithHandlestack:output:0Const:output:0Const_1:output:0RaggedConstant/values:output:0RaggedConstant/Const:output:0RaggedConstant/Const_1:output:0inference_op_model_handle*-
_output_shapes
:���������:*
dense_output_dim�
PartitionedCall_1PartitionedCall inference_op:dense_predictions:0'inference_op:dense_col_representation:0*
Tin
2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *1
f,R*
(__inference__finalize_predictions_191618i
IdentityIdentityPartitionedCall_1:output:0^NoOp*
T0*'
_output_shapes
:���������1
NoOpNoOp^inference_op*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 2
inference_opinference_op:,(
&
_user_specified_namemodel_handle:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�
�
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192471
energy_kcal_100g

fiber_100g9
5fruits_vegetables_nuts_estimate_from_ingredients_100g
proteins_100g
	salt_100g
saturated_fat_100g
sugars_100g
unknown
identity��StatefulPartitionedCall�
StatefulPartitionedCallStatefulPartitionedCallenergy_kcal_100g
fiber_100g5fruits_vegetables_nuts_estimate_from_ingredients_100gproteins_100g	salt_100gsaturated_fat_100gsugars_100gunknown*
Tin

2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *c
f^R\
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192445o
IdentityIdentity StatefulPartitionedCall:output:0^NoOp*
T0*'
_output_shapes
:���������<
NoOpNoOp^StatefulPartitionedCall*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 22
StatefulPartitionedCallStatefulPartitionedCall:&"
 
_user_specified_name192467:PL
#
_output_shapes
:���������
%
_user_specified_namesugars_100g:WS
#
_output_shapes
:���������
,
_user_specified_namesaturated-fat_100g:NJ
#
_output_shapes
:���������
#
_user_specified_name	salt_100g:RN
#
_output_shapes
:���������
'
_user_specified_nameproteins_100g:zv
#
_output_shapes
:���������
O
_user_specified_name75fruits-vegetables-nuts-estimate-from-ingredients_100g:OK
#
_output_shapes
:���������
$
_user_specified_name
fiber_100g:U Q
#
_output_shapes
:���������
*
_user_specified_nameenergy-kcal_100g
�
�
__inference_call_191621

inputs
inputs_5
inputs_4
inputs_6
inputs_3
inputs_2
inputs_1
inference_op_model_handle
identity��inference_op�
PartitionedCallPartitionedCallinputsinputs_5inputs_4inputs_6inputs_3inputs_2inputs_1*
Tin
	2*
Tout
	2*
_collective_manager_ids
 *}
_output_shapesk
i:���������:���������:���������:���������:���������:���������:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *4
f/R-
+__inference__build_normalized_inputs_191597�
stackPackPartitionedCall:output:0PartitionedCall:output:1PartitionedCall:output:2PartitionedCall:output:3PartitionedCall:output:4PartitionedCall:output:5PartitionedCall:output:6*
N*
T0*'
_output_shapes
:���������*

axisL
ConstConst*
_output_shapes
:  *
dtype0*
value
B  N
Const_1Const*
_output_shapes
:  *
dtype0*
value
B  X
RaggedConstant/valuesConst*
_output_shapes
: *
dtype0*
valueB ^
RaggedConstant/ConstConst*
_output_shapes
:*
dtype0	*
valueB	R `
RaggedConstant/Const_1Const*
_output_shapes
:*
dtype0	*
valueB	R �
inference_opSimpleMLInferenceOpWithHandlestack:output:0Const:output:0Const_1:output:0RaggedConstant/values:output:0RaggedConstant/Const:output:0RaggedConstant/Const_1:output:0inference_op_model_handle*-
_output_shapes
:���������:*
dense_output_dim�
PartitionedCall_1PartitionedCall inference_op:dense_predictions:0'inference_op:dense_col_representation:0*
Tin
2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *1
f,R*
(__inference__finalize_predictions_191618i
IdentityIdentityPartitionedCall_1:output:0^NoOp*
T0*'
_output_shapes
:���������1
NoOpNoOp^inference_op*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 2
inference_opinference_op:,(
&
_user_specified_namemodel_handle:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:KG
#
_output_shapes
:���������
 
_user_specified_nameinputs:K G
#
_output_shapes
:���������
 
_user_specified_nameinputs
�
�
__inference_call_191756
inputs_energy_kcal_100g
inputs_fiber_100g@
<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g
inputs_proteins_100g
inputs_salt_100g
inputs_saturated_fat_100g
inputs_sugars_100g
inference_op_model_handle
identity��inference_op�
PartitionedCallPartitionedCallinputs_energy_kcal_100ginputs_fiber_100g<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100ginputs_proteins_100ginputs_salt_100ginputs_saturated_fat_100ginputs_sugars_100g*
Tin
	2*
Tout
	2*
_collective_manager_ids
 *}
_output_shapesk
i:���������:���������:���������:���������:���������:���������:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *4
f/R-
+__inference__build_normalized_inputs_191597�
stackPackPartitionedCall:output:0PartitionedCall:output:1PartitionedCall:output:2PartitionedCall:output:3PartitionedCall:output:4PartitionedCall:output:5PartitionedCall:output:6*
N*
T0*'
_output_shapes
:���������*

axisL
ConstConst*
_output_shapes
:  *
dtype0*
value
B  N
Const_1Const*
_output_shapes
:  *
dtype0*
value
B  X
RaggedConstant/valuesConst*
_output_shapes
: *
dtype0*
valueB ^
RaggedConstant/ConstConst*
_output_shapes
:*
dtype0	*
valueB	R `
RaggedConstant/Const_1Const*
_output_shapes
:*
dtype0	*
valueB	R �
inference_opSimpleMLInferenceOpWithHandlestack:output:0Const:output:0Const_1:output:0RaggedConstant/values:output:0RaggedConstant/Const:output:0RaggedConstant/Const_1:output:0inference_op_model_handle*-
_output_shapes
:���������:*
dense_output_dim�
PartitionedCall_1PartitionedCall inference_op:dense_predictions:0'inference_op:dense_col_representation:0*
Tin
2*
Tout
2*
_collective_manager_ids
 *'
_output_shapes
:���������* 
_read_only_resource_inputs
 *2
config_proto" 

CPU

GPU 2J 8� �J *1
f,R*
(__inference__finalize_predictions_191618i
IdentityIdentityPartitionedCall_1:output:0^NoOp*
T0*'
_output_shapes
:���������1
NoOpNoOp^inference_op*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*~
_input_shapesm
k:���������:���������:���������:���������:���������:���������:���������: 2
inference_opinference_op:,(
&
_user_specified_namemodel_handle:WS
#
_output_shapes
:���������
,
_user_specified_nameinputs_sugars_100g:^Z
#
_output_shapes
:���������
3
_user_specified_nameinputs_saturated_fat_100g:UQ
#
_output_shapes
:���������
*
_user_specified_nameinputs_salt_100g:YU
#
_output_shapes
:���������
.
_user_specified_nameinputs_proteins_100g:�}
#
_output_shapes
:���������
V
_user_specified_name><inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g:VR
#
_output_shapes
:���������
+
_user_specified_nameinputs_fiber_100g:\ X
#
_output_shapes
:���������
1
_user_specified_nameinputs_energy_kcal_100g
�
�
__inference__initializer_192498
staticregexreplace_input>
:simple_ml_simplemlloadmodelfrompathwithhandle_model_handle
identity��-simple_ml/SimpleMLLoadModelFromPathWithHandle�
StaticRegexReplaceStaticRegexReplacestaticregexreplace_input*
_output_shapes
: *!
pattern2d240996bcaf49d4done*
rewrite �
-simple_ml/SimpleMLLoadModelFromPathWithHandle#SimpleMLLoadModelFromPathWithHandle:simple_ml_simplemlloadmodelfrompathwithhandle_model_handleStaticRegexReplace:output:0*
_output_shapes
 *!
file_prefix2d240996bcaf49d4G
ConstConst*
_output_shapes
: *
dtype0*
value	B :L
IdentityIdentityConst:output:0^NoOp*
T0*
_output_shapes
: R
NoOpNoOp.^simple_ml/SimpleMLLoadModelFromPathWithHandle*
_output_shapes
 "
identityIdentity:output:0*(
_construction_contextkEagerRuntime*
_input_shapes
: : 2^
-simple_ml/SimpleMLLoadModelFromPathWithHandle-simple_ml/SimpleMLLoadModelFromPathWithHandle:,(
&
_user_specified_namemodel_handle: 

_output_shapes
: 
�$
�
"__inference__traced_restore_192625
file_prefix%
assignvariableop_is_trained:
 &
assignvariableop_1_iteration:	 *
 assignvariableop_2_learning_rate: $
assignvariableop_3_total_1: $
assignvariableop_4_count_1: "
assignvariableop_5_total: "
assignvariableop_6_count: 

identity_8��AssignVariableOp�AssignVariableOp_1�AssignVariableOp_2�AssignVariableOp_3�AssignVariableOp_4�AssignVariableOp_5�AssignVariableOp_6�
RestoreV2/tensor_namesConst"/device:CPU:0*
_output_shapes
:*
dtype0*�
value�B�B&_is_trained/.ATTRIBUTES/VARIABLE_VALUEB0optimizer/_iterations/.ATTRIBUTES/VARIABLE_VALUEB3optimizer/_learning_rate/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/0/total/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/0/count/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/1/total/.ATTRIBUTES/VARIABLE_VALUEB4keras_api/metrics/1/count/.ATTRIBUTES/VARIABLE_VALUEB_CHECKPOINTABLE_OBJECT_GRAPH�
RestoreV2/shape_and_slicesConst"/device:CPU:0*
_output_shapes
:*
dtype0*#
valueBB B B B B B B B �
	RestoreV2	RestoreV2file_prefixRestoreV2/tensor_names:output:0#RestoreV2/shape_and_slices:output:0"/device:CPU:0*4
_output_shapes"
 ::::::::*
dtypes

2
	[
IdentityIdentityRestoreV2:tensors:0"/device:CPU:0*
T0
*
_output_shapes
:�
AssignVariableOpAssignVariableOpassignvariableop_is_trainedIdentity:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0
]

Identity_1IdentityRestoreV2:tensors:1"/device:CPU:0*
T0	*
_output_shapes
:�
AssignVariableOp_1AssignVariableOpassignvariableop_1_iterationIdentity_1:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0	]

Identity_2IdentityRestoreV2:tensors:2"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_2AssignVariableOp assignvariableop_2_learning_rateIdentity_2:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_3IdentityRestoreV2:tensors:3"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_3AssignVariableOpassignvariableop_3_total_1Identity_3:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_4IdentityRestoreV2:tensors:4"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_4AssignVariableOpassignvariableop_4_count_1Identity_4:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_5IdentityRestoreV2:tensors:5"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_5AssignVariableOpassignvariableop_5_totalIdentity_5:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0]

Identity_6IdentityRestoreV2:tensors:6"/device:CPU:0*
T0*
_output_shapes
:�
AssignVariableOp_6AssignVariableOpassignvariableop_6_countIdentity_6:output:0"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 *
dtype0Y
NoOpNoOp"/device:CPU:0*&
 _has_manual_control_dependencies(*
_output_shapes
 �

Identity_7Identityfile_prefix^AssignVariableOp^AssignVariableOp_1^AssignVariableOp_2^AssignVariableOp_3^AssignVariableOp_4^AssignVariableOp_5^AssignVariableOp_6^NoOp"/device:CPU:0*
T0*
_output_shapes
: U

Identity_8IdentityIdentity_7:output:0^NoOp_1*
T0*
_output_shapes
: �
NoOp_1NoOp^AssignVariableOp^AssignVariableOp_1^AssignVariableOp_2^AssignVariableOp_3^AssignVariableOp_4^AssignVariableOp_5^AssignVariableOp_6*
_output_shapes
 "!

identity_8Identity_8:output:0*(
_construction_contextkEagerRuntime*#
_input_shapes
: : : : : : : : 2(
AssignVariableOp_1AssignVariableOp_12(
AssignVariableOp_2AssignVariableOp_22(
AssignVariableOp_3AssignVariableOp_32(
AssignVariableOp_4AssignVariableOp_42(
AssignVariableOp_5AssignVariableOp_52(
AssignVariableOp_6AssignVariableOp_62$
AssignVariableOpAssignVariableOp:%!

_user_specified_namecount:%!

_user_specified_nametotal:'#
!
_user_specified_name	count_1:'#
!
_user_specified_name	total_1:-)
'
_user_specified_namelearning_rate:)%
#
_user_specified_name	iteration:*&
$
_user_specified_name
is_trained:C ?

_output_shapes
: 
%
_user_specified_namefile_prefix
�
\
(__inference__finalize_predictions_191618
predictions
predictions_1
identityS
IdentityIdentitypredictions*
T0*'
_output_shapes
:���������"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*,
_input_shapes
:���������::GC

_output_shapes
:
%
_user_specified_namepredictions:T P
'
_output_shapes
:���������
%
_user_specified_namepredictions
�
�
(__inference__finalize_predictions_191729!
predictions_dense_predictions(
$predictions_dense_col_representation
identitye
IdentityIdentitypredictions_dense_predictions*
T0*'
_output_shapes
:���������"
identityIdentity:output:0*(
_construction_contextkEagerRuntime*,
_input_shapes
:���������::`\

_output_shapes
:
>
_user_specified_name&$predictions_dense_col_representation:f b
'
_output_shapes
:���������
7
_user_specified_namepredictions_dense_predictions"�L
saver_filename:0StatefulPartitionedCall_2:0StatefulPartitionedCall_38"
saved_model_main_op

NoOp*>
__saved_model_init_op%#
__saved_model_init_op

NoOp*�
serving_default�
I
energy-kcal_100g5
"serving_default_energy-kcal_100g:0���������
=

fiber_100g/
serving_default_fiber_100g:0���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gZ
Gserving_default_fruits-vegetables-nuts-estimate-from-ingredients_100g:0���������
C
proteins_100g2
serving_default_proteins_100g:0���������
;
	salt_100g.
serving_default_salt_100g:0���������
M
saturated-fat_100g7
$serving_default_saturated-fat_100g:0���������
?
sugars_100g0
serving_default_sugars_100g:0���������<
output_10
StatefulPartitionedCall:0���������tensorflow/serving/predict22

asset_path_initializer:02d240996bcaf49d4done29

asset_path_initializer_1:02d240996bcaf49d4header.pb2P

asset_path_initializer_2:002d240996bcaf49d4gradient_boosted_trees_header.pb2D

asset_path_initializer_3:0$2d240996bcaf49d4nodes-00000-of-000012<

asset_path_initializer_4:02d240996bcaf49d4data_spec.pb:�y
�
	variables
trainable_variables
regularization_losses
	keras_api
__call__
*&call_and_return_all_conditional_losses
_default_save_signature

_multitask
	_is_trained

_learner_params
	_features
	optimizer
loss
_models
_build_normalized_inputs
_finalize_predictions
call
call_get_leaves
yggdrasil_model_path_tensor

signatures"
_tf_keras_model
'
	0"
trackable_list_wrapper
 "
trackable_list_wrapper
 "
trackable_list_wrapper
�
non_trainable_variables

layers
metrics
layer_regularization_losses
layer_metrics
	variables
trainable_variables
regularization_losses
__call__
_default_save_signature
*&call_and_return_all_conditional_losses
&"call_and_return_conditional_losses"
_generic_user_object
�
trace_0
trace_12�
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192458
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192471�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults�
p 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 ztrace_0ztrace_1
�
trace_0
trace_12�
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192418
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192445�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults�
p 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 ztrace_0ztrace_1
�B�
!__inference__wrapped_model_192391energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec
args�

jargs_0
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
 "
trackable_list_wrapper
:
 2
is_trained
 "
trackable_dict_wrapper
 "
trackable_list_wrapper
j

_variables
_iterations
 _learning_rate
!_update_step_xla"
experimentalOptimizer
 "
trackable_dict_wrapper
'
"0"
trackable_list_wrapper
�
#trace_02�
+__inference__build_normalized_inputs_191724�
���
FullArgSpec
args�

jinputs
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 z#trace_0
�
$trace_02�
(__inference__finalize_predictions_191729�
���
FullArgSpec1
args)�&
jtask
jpredictions
jlike_engine
varargs
 
varkw
 
defaults�
p 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 z$trace_0
�
%trace_02�
__inference_call_191756�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults�
p 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 z%trace_0
�2��
���
FullArgSpec
args�

jinputs
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�
&trace_02�
.__inference_yggdrasil_model_path_tensor_191761�
���
FullArgSpec$
args�
jmultitask_model_index
varargs
 
varkw
 
defaults�
` 

kwonlyargs� 
kwonlydefaults
 
annotations� *� z&trace_0
,
'serving_default"
signature_map
'
	0"
trackable_list_wrapper
 "
trackable_list_wrapper
.
(0
)1"
trackable_list_wrapper
 "
trackable_list_wrapper
 "
trackable_dict_wrapper
�B�
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192458energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192471energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192418energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192445energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
'
0"
trackable_list_wrapper
:	 2	iteration
: 2learning_rate
�2��
���
FullArgSpec*
args"�

jgradient

jvariable
jkey
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 0
G
*_input_builder
+_compiled_model"
_generic_user_object
�B�
+__inference__build_normalized_inputs_191724inputs_energy_kcal_100ginputs_fiber_100g<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100ginputs_proteins_100ginputs_salt_100ginputs_saturated_fat_100ginputs_sugars_100g"�
���
FullArgSpec
args�

jinputs
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
(__inference__finalize_predictions_191729predictions_dense_predictions$predictions_dense_col_representation"�
���
FullArgSpec1
args)�&
jtask
jpredictions
jlike_engine
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�B�
__inference_call_191756inputs_energy_kcal_100ginputs_fiber_100g<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100ginputs_proteins_100ginputs_salt_100ginputs_saturated_fat_100ginputs_sugars_100g"�
���
FullArgSpec!
args�
jinputs

jtraining
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 
�
,	capture_0B�
.__inference_yggdrasil_model_path_tensor_191761"�
���
FullArgSpec$
args�
jmultitask_model_index
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *
 z,	capture_0
�B�
$__inference_signature_wrapper_192487energy-kcal_100g
fiber_100g5fruits-vegetables-nuts-estimate-from-ingredients_100gproteins_100g	salt_100gsaturated-fat_100gsugars_100g"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 �

kwonlyargs���
jenergy_kcal_100g
j
fiber_100g
7j5fruits_vegetables_nuts_estimate_from_ingredients_100g
jproteins_100g
j	salt_100g
jsaturated_fat_100g
jsugars_100g
kwonlydefaults
 
annotations� *
 
N
-	variables
.	keras_api
	/total
	0count"
_tf_keras_metric
^
1	variables
2	keras_api
	3total
	4count
5
_fn_kwargs"
_tf_keras_metric
l
6_feature_name_to_idx
7	_init_ops
#8categorical_str_to_int_hashmaps"
_generic_user_object
S
9_model_loader
:_create_resource
;_initialize
<_destroy_resourceR 
* 
.
/0
01"
trackable_list_wrapper
-
-	variables"
_generic_user_object
:  (2total
:  (2count
.
30
41"
trackable_list_wrapper
-
1	variables"
_generic_user_object
:  (2total
:  (2count
 "
trackable_dict_wrapper
 "
trackable_dict_wrapper
 "
trackable_list_wrapper
 "
trackable_dict_wrapper
Q
=_output_types
>
_all_files
,
_done_file"
_generic_user_object
�
?trace_02�
__inference__creator_192491�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� z?trace_0
�
@trace_02�
__inference__initializer_192498�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� z@trace_0
�
Atrace_02�
__inference__destroyer_192502�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� zAtrace_0
 "
trackable_list_wrapper
C
B0
C1
,2
D3
E4"
trackable_list_wrapper
�B�
__inference__creator_192491"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� 
�
,	capture_0B�
__inference__initializer_192498"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� z,	capture_0
�B�
__inference__destroyer_192502"�
���
FullArgSpec
args� 
varargs
 
varkw
 
defaults
 

kwonlyargs� 
kwonlydefaults
 
annotations� *� 
*
*
*
*�
+__inference__build_normalized_inputs_191724����
���
���
A
energy-kcal_100g-�*
inputs_energy_kcal_100g���������
5

fiber_100g'�$
inputs_fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gR�O
<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g���������
;
proteins_100g*�'
inputs_proteins_100g���������
3
	salt_100g&�#
inputs_salt_100g���������
E
saturated-fat_100g/�,
inputs_saturated_fat_100g���������
7
sugars_100g(�%
inputs_sugars_100g���������
� "���
:
energy-kcal_100g&�#
energy_kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits_vegetables_nuts_estimate_from_ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated_fat_100g���������
0
sugars_100g!�
sugars_100g���������@
__inference__creator_192491!�

� 
� "�
unknown B
__inference__destroyer_192502!�

� 
� "�
unknown �
(__inference__finalize_predictions_191729����
���
`
���
ModelOutputL
dense_predictions7�4
predictions_dense_predictions���������M
dense_col_representation1�.
$predictions_dense_col_representation
p 
� "!�
unknown���������H
__inference__initializer_192498%,+�

� 
� "�
unknown �
!__inference__wrapped_model_192391�+���
���
���
:
energy-kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������
� "3�0
.
output_1"�
output_1����������
__inference_call_191756�+���
���
���
A
energy-kcal_100g-�*
inputs_energy_kcal_100g���������
5

fiber_100g'�$
inputs_fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gR�O
<inputs_fruits_vegetables_nuts_estimate_from_ingredients_100g���������
;
proteins_100g*�'
inputs_proteins_100g���������
3
	salt_100g&�#
inputs_salt_100g���������
E
saturated-fat_100g/�,
inputs_saturated_fat_100g���������
7
sugars_100g(�%
inputs_sugars_100g���������
p 
� "!�
unknown����������
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192418�+���
���
���
:
energy-kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������
p
� ",�)
"�
tensor_0���������
� �
Z__inference_gradient_boosted_trees_model_1_layer_call_and_return_conditional_losses_192445�+���
���
���
:
energy-kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������
p 
� ",�)
"�
tensor_0���������
� �
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192458�+���
���
���
:
energy-kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������
p
� "!�
unknown����������
?__inference_gradient_boosted_trees_model_1_layer_call_fn_192471�+���
���
���
:
energy-kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits-vegetables-nuts-estimate-from-ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated-fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������
p 
� "!�
unknown����������
$__inference_signature_wrapper_192487�+���
� 
���
:
energy_kcal_100g&�#
energy-kcal_100g���������
.

fiber_100g �

fiber_100g���������
�
5fruits_vegetables_nuts_estimate_from_ingredients_100gK�H
5fruits-vegetables-nuts-estimate-from-ingredients_100g���������
4
proteins_100g#� 
proteins_100g���������
,
	salt_100g�
	salt_100g���������
>
saturated_fat_100g(�%
saturated-fat_100g���������
0
sugars_100g!�
sugars_100g���������"3�0
.
output_1"�
output_1���������Z
.__inference_yggdrasil_model_path_tensor_191761(,�
�
` 
� "�
unknown 