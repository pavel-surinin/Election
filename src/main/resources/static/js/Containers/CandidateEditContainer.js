var CandidateEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      surname: '',
      date: '',
      party: '',
      comment: '',
    };
  },

  onHandleNameChange : function(event){
    this.setState({title: event.target.value});
  },

  onHandleSurnameChange : function(event){
    this.setState({county: event.target.value});
  },

  onHandleDateChange : function(event){
    this.setState({representative : event.target.value});
  },

  onHandlePartyChange : function(event){
    this.setState({representative : event.target.value});
  },
	  
  onHandleCommentChange : function(event){
    this.setState({representative : event.target.value});
  },
  
  onHandleSubmit: function(event){
    var self = this;
    event.preventDefault();
    axios.post('/api/candidates', this.state)
    .then(function(response){
      console.log(response);
      self.context.router.push('/');
    })
    .catch(function(err){
      console.log('CandidateEditContainer.onHandleSubmit - ',err);
    });

  },

  onHandleCancel:function(){
    this.context.router.push('/');
  },

  render: function() {
    return (
      <div>
      <CandidateEditComponent
          submitButtonName='Išsaugoti'

          name={this.state.name}
          surname={this.state.surname}
          date={this.state.date}
          party={this.state.party}
          comment={this.state.comment}

          onHandleNameChange={this.onHandleNameChange}
          onHandleSurnameChange={this.onHandleSurnameChange}
          onHandleDateChange={this.onHandleDateChange}
          onHandlePartyChange={this.onHandlePartyChange}
          onHandleCommentChange={this.onHandleCommentChange}

          onHandleSubmit={this.onHandleSubmit}
          onHandleCancel={this.onHandleCancel}

        />
      </div>
    );
  }
});

CandidateEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.CandidateEditContainer = CandidateEditContainer;
