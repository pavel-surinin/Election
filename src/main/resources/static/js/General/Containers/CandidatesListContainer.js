var CandidatesListContainer = React.createClass({

  getInitialState: function() {
    return {
      candidateList : [],
      isLoading : true,
      succesCreateText: '',
    };
  },

  componentWillMount: function() {
    var self = this;
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    axios
      .get('/candidate')
      .then(function(response){
        self.setState({
          candidateList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
          console.error('CandidatesListContainer.componentWillMount.axios.get.candidate', err);
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
        <CandidatesList
          candidateList={this.state.candidateList}
          succesCreateText={this.state.succesCreateText}
        />
      );
    }

  }

});

window.CandidatesListContainer = CandidatesListContainer;
