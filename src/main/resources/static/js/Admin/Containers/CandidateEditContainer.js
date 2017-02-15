var CandidateEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      surname: '',
      birthDate: '',
      description: '',
      numberInParty: '',
      partyDependencies: '',
      partijosId: '',
      candidateInfo : [],
      isloading: false,
      countyId : null,
    };
  },
  componentWillMount: function(){
    var self = this;
    axios
      .get('/candidate/'+ this.props.params.id)
      .then(function(response){
        self.setState({
          name : response.data.name.trim(),
          surname : response.data.surname.trim(),
          birthDate : response.data.birthDate,
          description : response.data.description,
          numberInParty : response.data.numberInParty,
          partijosId : response.data.partijosId,
          countyId : response.data.countyId,
        });
      })
      .catch(function(err){
        console.error('CandidateEditContainer.componentWillMount.axios.getCandidateById', err);
      });
  },
  onHandleNameChange : function(event){
    this.setState({name: event.target.value});
  },
  onHandleSurnameChange : function(event){
    this.setState({surname: event.target.value});
  },
  onHandleBirthDateChange : function(event){
    this.setState({birthDate : event.target.value});
  },
  onHandleDescriptionChange : function(event){
    this.setState({description : event.target.value});
  },
  onHandleNumberInPartyChange : function(event){
    this.setState({numberInParty : event.target.value});
  },
  onHandleSubmit: function(event){
    var self = this;
    var pd = null;
    if (this.state.partijosId != null) {
      pd = {id : this.state.partijosId};
    }

    var countyD = null;
    if (this.state.countyId != null) {
      countyD = {id : this.state.countyId};
    }
    var postCandidateObject = {
      id: this.props.params.id,
      name : this.state.name.trim(),
      surname : this.state.surname.trim(),
      birthDate : new Date(this.state.birthDate),
      description : this.state.description,
      numberInParty : this.state.numberInParty,
      partyDependencies : pd,
      county : countyD,
    };
    console.log(postCandidateObject);
    event.preventDefault();
    axios.post('/candidate', postCandidateObject)
    .then(function(response){
      console.log(response);
      self.context.router.push('/admin/candidate?succesCreateText=Kandidatas ' + self.state.name + ' ' + self.state.surname + ' atnaujintas');
    })
    .catch(function(err){
      console.log('CandidateEditContainer.onHandleSubmit.axios.editCandidate',err);
    });
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
    return (
        <div>
            <CandidateEditFormComponent
                name={this.state.name}
                surname={this.state.surname}
                birthDate={this.state.birthDate}
                description={this.state.description}
                numberInParty={this.state.numberInParty}
                partyDependencies={this.state.partyDependencies}
                partijosId={this.state.partijosId}
                countyId={this.state.countyId}
                onHandleNameChange={this.onHandleNameChange}
                onHandleSurnameChange={this.onHandleSurnameChange}
                onHandleBirthDateChange={this.onHandleBirthDateChange}
                onHandleDescriptionChange={this.onHandleDescriptionChange}
                onHandleNumberInPartyChange={this.onHandleNumberInPartyChange}
                onHandleSubmit={this.onHandleSubmit}
                succesCreateText={this.state.succesCreateText}
                action='Atnaujinti'
              />
          </div>
        );
      }
    }
});

CandidateEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.CandidateEditContainer = CandidateEditContainer;
