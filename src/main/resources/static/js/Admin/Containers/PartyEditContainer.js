var PartyEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      number : '',
      file : null,
      nameErrorMesages : [],
      numberErrorMesages : [],
      fileErrorMesages : [],
      postErrCode : 199,
    };
  },
  componentWillMount: function() {
      var self = this;
      axios
        .get('/party/' + this.props.params.id)
        .then(function(response){
          self.setState({
            name : response.data.name.trim(),
            number : response.data.partyNumber,
          });
        })
        .catch(function(err){
          console.error('DistrictEditContainer.componentWillMount.axios.getCouty', err);
        });
    },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       onHandleNumberChange={this.onHandleNumberChange}
       onHandleFileChange={this.onHandleFileChange}
       onHandleSubmit={this.onHandleSubmit}
       name={this.state.name}
       number={this.state.number}
       nameErrorMesages={this.state.nameErrorMesages}
       numberErrorMesages={this.state.numberErrorMesages}
       fileErrorMesages={this.state.fileErrorMesages}
       action='Atnaujinti'
       />
    );
  },

});

window.PartyEditContainer = PartyEditContainer;
